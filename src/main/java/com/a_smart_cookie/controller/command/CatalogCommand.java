package com.a_smart_cookie.controller.command;

import com.a_smart_cookie.controller.route.HttpHandlerType;
import com.a_smart_cookie.controller.route.HttpPath;
import com.a_smart_cookie.controller.route.WebPath;
import com.a_smart_cookie.entity.Language;
import com.a_smart_cookie.entity.Publication;
import com.a_smart_cookie.exception.ServiceException;
import com.a_smart_cookie.service.PublicationService;
import com.a_smart_cookie.service.impl.PublicationServiceImpl;
import com.a_smart_cookie.util.pagination.ItemsPerPage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class CatalogCommand extends Command {

	private static final long serialVersionUID = -266627794194940139L;

	@Override
	public HttpPath execute(HttpServletRequest request, HttpServletResponse response) {
		try {
			ItemsPerPage itemsPerPage = ItemsPerPage.fromString(request.getParameter("limit"));

			int currentPage = 1;
			String pageNumberParameter = request.getParameter("page");
			if (pageNumberParameter != null && Integer.parseInt(pageNumberParameter) >= currentPage ) {
				currentPage = Integer.parseInt(pageNumberParameter);
			}

			Language language = Language.fromString(request.getParameter("lang"));

			PublicationService publicationService = new PublicationServiceImpl();
			List<Publication> publications = publicationService.findLimitedWithOffsetByLanguage(
					itemsPerPage.getLimit(),
					itemsPerPage.getLimit() * --currentPage,
					language);

			if (publications.isEmpty()) {
				return new HttpPath(WebPath.Command.CATALOG_FIRST_PAGE, HttpHandlerType.SEND_REDIRECT);
			}

			System.out.println(publications);
			request.setAttribute("language", language);
			request.setAttribute("publications", publications);

			return new HttpPath(WebPath.Page.CATALOG, HttpHandlerType.FORWARD);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return new HttpPath(WebPath.Page.ERROR, HttpHandlerType.SEND_REDIRECT);
	}

}
