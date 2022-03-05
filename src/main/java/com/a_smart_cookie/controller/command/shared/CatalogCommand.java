package com.a_smart_cookie.controller.command.shared;

import com.a_smart_cookie.controller.command.Command;
import com.a_smart_cookie.controller.route.HttpHandlerType;
import com.a_smart_cookie.controller.route.HttpPath;
import com.a_smart_cookie.controller.route.WebPath;
import com.a_smart_cookie.dto.catalog.CountRowsParameters;
import com.a_smart_cookie.dto.catalog.FilterParameters;
import com.a_smart_cookie.dto.catalog.PublicationsWithAllUsedGenres;
import com.a_smart_cookie.entity.Genre;
import com.a_smart_cookie.entity.Language;
import com.a_smart_cookie.entity.Publication;
import com.a_smart_cookie.exception.ServiceException;
import com.a_smart_cookie.service.PublicationService;
import com.a_smart_cookie.service.ServiceFactory;
import com.a_smart_cookie.util.CookieHandler;
import com.a_smart_cookie.util.pagination.ItemsPerPage;
import com.a_smart_cookie.util.pagination.PaginationHandler;
import com.a_smart_cookie.util.sorting.SortingDirection;
import com.a_smart_cookie.util.sorting.SortingParameter;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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

		Genre genreRestriction = Genre.fromString(request.getParameter("specificGenre"));
		String searchedTitle = request.getParameter("search");
		Language language = Language.safeFromString(CookieHandler.readCookieValue(request, "lang").orElse(Language.UKRAINIAN.getAbbr()));
		SortingParameter activeSortingParam = SortingParameter.safeFromString(request.getParameter("sort"));
		SortingDirection activeSortingDirection = SortingDirection.safeFromString(request.getParameter("direction"));

		PublicationsWithAllUsedGenres publicationsWithAllUsedGenres;
		int numberOfPages;
		int currentPage;
		int itemsPerPage;

		try {
			PublicationService publicationService = ServiceFactory.getInstance().getPublicationService();
			itemsPerPage = ItemsPerPage.safeFromString(request.getParameter("limit")).getLimit();
			int totalNumberOfFoundedPublications = publicationService
					.getTotalNumberOfRequestedQueryRows(new CountRowsParameters(language, genreRestriction, searchedTitle));
			numberOfPages = PaginationHandler.getRequestedNumberOfPages(itemsPerPage, totalNumberOfFoundedPublications);
			currentPage = PaginationHandler.getRequestedPageNumber(request, numberOfPages);

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

			publicationsWithAllUsedGenres = publicationService
					.findPublicationsByFilterParameters(filterParameters);

		} catch (ServiceException e) {
			HttpSession session = request.getSession();
			session.setAttribute("serviceError", new Object());
			LOG.error("Exception has occurred on service layer", e);
			return new HttpPath(WebPath.Command.CATALOG_FIRST_PAGE, HttpHandlerType.SEND_REDIRECT);
		}

		List<Publication> publications = publicationsWithAllUsedGenres.getPublications();
		LOG.trace("Found in DB publications --> " + publications);
		List<Genre> usedGenres = publicationsWithAllUsedGenres.getGenres();

		if (searchedTitle != null) {
			request.setAttribute("search", searchedTitle);
		}

		if (genreRestriction != null) {
			request.setAttribute("specificGenre", genreRestriction);
		}

		request.setAttribute("publications", publications);
		request.setAttribute("genres", usedGenres);

		PaginationHandler.setPaginationAttributes(request, numberOfPages, currentPage, itemsPerPage);

		request.setAttribute("sort", activeSortingParam.getValue().toLowerCase());
		request.setAttribute("direction", activeSortingDirection.name().toLowerCase());

		LOG.debug("Command finished with success");
		return new HttpPath(WebPath.Page.CATALOG, HttpHandlerType.FORWARD);
	}

}
