package com.a_smart_cookie.controller.command;

import com.a_smart_cookie.dto.catalog.CountRowsParameters;
import com.a_smart_cookie.dto.catalog.FilterParameters;
import com.a_smart_cookie.controller.route.HttpHandlerType;
import com.a_smart_cookie.controller.route.HttpPath;
import com.a_smart_cookie.controller.route.WebPath;
import com.a_smart_cookie.dto.catalog.PublicationsWithAllUsedGenres;
import com.a_smart_cookie.entity.Language;
import com.a_smart_cookie.entity.Publication;
import com.a_smart_cookie.exception.ServiceException;
import com.a_smart_cookie.service.PublicationService;
import com.a_smart_cookie.service.ServiceFactory;
import com.a_smart_cookie.util.CookieHandler;
import com.a_smart_cookie.util.pagination.ItemsPerPage;
import com.a_smart_cookie.util.sorting.SortingDirection;
import com.a_smart_cookie.util.sorting.SortingParameter;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Provides with catalog page.
 *
 */
public class CatalogCommand extends Command {

	private static final long serialVersionUID = -266627794194940139L;

	private static final Logger LOG = Logger.getLogger(CatalogCommand.class);

	@Override
	public HttpPath execute(HttpServletRequest request, HttpServletResponse response) {
		LOG.debug("Command starts");

		try {
			PublicationService publicationService = ServiceFactory.getInstance().getPublicationService();

			Publication.Genre genreRestriction = Publication.Genre.fromString(request.getParameter("specificGenre"));
			String searchedTitle = request.getParameter("search");
			Language language = Language.safeFromString(CookieHandler.readCookieValue(request, "lang").orElse(Language.UKRAINIAN.getAbbr()));
			int itemsPerPage = ItemsPerPage.safeFromString(request.getParameter("limit")).getLimit();
			int totalNumberOfFoundedPublications = publicationService
					.getTotalNumberOfRequestedQueryRows(new CountRowsParameters(language, genreRestriction, searchedTitle));
			int numberOfPages = getNumberOfPages(itemsPerPage, totalNumberOfFoundedPublications);
			int currentPage = getCurrentPage(request, numberOfPages);
			SortingParameter activeSortingParam = SortingParameter.safeFromString(request.getParameter("sort"));
			SortingDirection activeSortingDirection = SortingDirection.safeFromString(request.getParameter("direction"));

			FilterParameters filterParameters = new FilterParameters(
					itemsPerPage,
					itemsPerPage * (currentPage-1),
					language,
					activeSortingDirection,
					activeSortingParam,
					genreRestriction,
					searchedTitle
			);
			LOG.trace("Queried filter parameters --> " + filterParameters);

			PublicationsWithAllUsedGenres publicationsWithAllUsedGenres = publicationService
					.findPublicationsByFilterParameters(filterParameters);

			List<Publication> publications = publicationsWithAllUsedGenres.getPublications();
			LOG.trace("Found in DB publications --> " + publications);
			List<Publication.Genre> usedGenres = publicationsWithAllUsedGenres.getGenres();
			LOG.trace("Found in DB used genres --> " + publications);


			if (searchedTitle != null) {
				request.setAttribute("search", searchedTitle);
			}

			if (genreRestriction != null) {
				request.setAttribute("specificGenre", genreRestriction);
			}

			request.setAttribute("publications", publications);
			request.setAttribute("genres", usedGenres);

			request.setAttribute("numberOfPages", numberOfPages);
			request.setAttribute("currentPage", currentPage);
			request.setAttribute("itemsPerPage", itemsPerPage);

			request.setAttribute("sort", activeSortingParam.getValue().toLowerCase());
			request.setAttribute("direction", activeSortingDirection.name().toLowerCase());

			LOG.debug("Command finished with success");
			return new HttpPath(WebPath.Page.CATALOG, HttpHandlerType.FORWARD);

		} catch (ServiceException e) {
			LOG.debug("Command ended with exception");
			return new HttpPath(WebPath.Page.ERROR, HttpHandlerType.SEND_REDIRECT);
		}
	}

	private int getNumberOfPages(int itemsPerPage, int totalNumberOfPublications) {
		int numberOfPages = totalNumberOfPublications / itemsPerPage;
		if (totalNumberOfPublications % itemsPerPage > 0) {
			++numberOfPages;
		}
		LOG.trace("Total number of requested pages -->" + numberOfPages);
		return numberOfPages;
	}

	private int getCurrentPage(HttpServletRequest request, int numberOfPages) {
		int currentPage = 1;
		String pageNumberParameter = request.getParameter("page");
		if (pageNumberParameter != null && (Integer.parseInt(pageNumberParameter) >= currentPage) && (Integer.parseInt(pageNumberParameter) <= numberOfPages)) {
			currentPage = Integer.parseInt(pageNumberParameter);
		}
		LOG.trace("Current page number -->" + currentPage);
		return currentPage;
	}

}
