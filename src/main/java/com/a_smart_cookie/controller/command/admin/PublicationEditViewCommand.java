package com.a_smart_cookie.controller.command.admin;

import com.a_smart_cookie.controller.command.Command;
import com.a_smart_cookie.controller.route.HttpHandlerType;
import com.a_smart_cookie.controller.route.HttpPath;
import com.a_smart_cookie.controller.route.WebPath;
import com.a_smart_cookie.entity.Language;
import com.a_smart_cookie.entity.Publication;
import com.a_smart_cookie.exception.ServiceException;
import com.a_smart_cookie.service.PublicationService;
import com.a_smart_cookie.service.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Provides with forwarding to page for editing  publication.
 *
 */
public class PublicationEditViewCommand extends Command {

	private static final long serialVersionUID = 1225508057310716306L;

	private static final Logger LOG = Logger.getLogger(PublicationEditViewCommand.class);

	@Override
	public HttpPath execute(HttpServletRequest request, HttpServletResponse response) {
		LOG.debug("Command starts");

		String publicationIdParam = request.getParameter("item");

		if (publicationIdParam == null) {
			HttpSession session = request.getSession();
			session.setAttribute("illegalParams", new Object());
			LOG.error("No publication id in request");
			return new HttpPath(WebPath.Command.ADMIN_PUBLICATIONS_MANAGEMENT, HttpHandlerType.SEND_REDIRECT);
		}

		Map<Language, Publication> publicationMap;

		try {
			PublicationService publicationService = ServiceFactory.getInstance().getPublicationService();
			publicationMap = publicationService.getPublicationInAllLanguagesById(Integer.parseInt(publicationIdParam));
		} catch (ServiceException e) {
			HttpSession session = request.getSession();
			session.setAttribute("serviceError", new Object());
			LOG.error("Exception has occurred on service layer", e);
			return new HttpPath(WebPath.Command.ADMIN_PUBLICATIONS_MANAGEMENT, HttpHandlerType.SEND_REDIRECT);
		}

		request.setAttribute("publicationMap", publicationMap);
		request.setAttribute("genre", publicationMap.entrySet().iterator().next().getValue().getGenre());
		request.setAttribute("pricePerMonth", publicationMap.entrySet().iterator().next().getValue().getPricePerMonth());
		request.setAttribute("id", publicationMap.entrySet().iterator().next().getValue().getId());

		LOG.debug("Command finished");
		return new HttpPath(WebPath.Page.ADMIN_PUBLICATION_EDIT, HttpHandlerType.FORWARD);
	}

}
