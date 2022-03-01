package com.a_smart_cookie.controller.command.admin;

import com.a_smart_cookie.controller.command.Command;
import com.a_smart_cookie.controller.route.HttpHandlerType;
import com.a_smart_cookie.controller.route.HttpPath;
import com.a_smart_cookie.controller.route.WebPath;
import com.a_smart_cookie.entity.Language;
import com.a_smart_cookie.entity.Publication;
import com.a_smart_cookie.entity.User;
import com.a_smart_cookie.service.PublicationService;
import com.a_smart_cookie.service.ServiceFactory;
import com.a_smart_cookie.util.CookieHandler;
import com.a_smart_cookie.util.pagination.PaginationHandler;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Provides with CRUD operation page on publications for admin.
 *
 */
public class PublicationsManagementCommand extends Command {

	private static final long serialVersionUID = -2137507939125502082L;

	private static final Logger LOG = Logger.getLogger(PublicationsManagementCommand.class);

	private static final int ITEMS_PER_PAGE = 10;

	@Override
	public HttpPath execute(HttpServletRequest request, HttpServletResponse response) {
		LOG.debug("Command starts");

		HttpSession session = request.getSession();

		User user = (User) session.getAttribute("user");
		session.invalidate();
		session = request.getSession();
		session.setAttribute("user", user);

		Language language = Language.safeFromString(CookieHandler.readCookieValue(request, "lang").orElse(Language.UKRAINIAN.getAbbr()));

		PublicationService publicationService = ServiceFactory.getInstance().getPublicationService();

		int totalNumberOfPublications = publicationService.getTotalNumberOfPublications();
		int numberOfPages = PaginationHandler.getRequestedNumberOfPages(ITEMS_PER_PAGE, totalNumberOfPublications);
		int currentPage = PaginationHandler.getRequestedPageNumber(request, numberOfPages);

		List<Publication> publications = publicationService.getLimitedPublicationsByLanguage(currentPage, ITEMS_PER_PAGE, language);
		LOG.trace("publications --> " + publications);

		request.setAttribute("publications", publications);

		PaginationHandler.setPaginationAttributes(request, numberOfPages, currentPage, ITEMS_PER_PAGE);

		LOG.debug("Command finished");
		return new HttpPath(WebPath.Page.ADMIN_PUBLICATIONS, HttpHandlerType.FORWARD);
	}

}
