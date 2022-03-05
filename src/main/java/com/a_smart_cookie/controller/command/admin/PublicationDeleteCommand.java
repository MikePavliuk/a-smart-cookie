package com.a_smart_cookie.controller.command.admin;

import com.a_smart_cookie.controller.command.Command;
import com.a_smart_cookie.controller.route.HttpHandlerType;
import com.a_smart_cookie.controller.route.HttpPath;
import com.a_smart_cookie.controller.route.WebPath;
import com.a_smart_cookie.exception.ServiceException;
import com.a_smart_cookie.service.PublicationService;
import com.a_smart_cookie.service.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Provides with deleting publication.
 *
 */
public class PublicationDeleteCommand extends Command {

	private static final long serialVersionUID = -2642262474210024586L;

	private static final Logger LOG = Logger.getLogger(PublicationDeleteCommand.class);

	@Override
	public HttpPath execute(HttpServletRequest request, HttpServletResponse response) {
		LOG.debug("Command starts");

		String publicationIdParam = request.getParameter("publicationId");

		if (publicationIdParam == null) {
			HttpSession session = request.getSession();
			session.setAttribute("illegalParams", new Object());
			LOG.error("No publication id in request");
			return new HttpPath(WebPath.Command.ADMIN_PUBLICATIONS_MANAGEMENT, HttpHandlerType.SEND_REDIRECT);
		}

		try {
			PublicationService publicationService = ServiceFactory.getInstance().getPublicationService();
			publicationService.deletePublication(Integer.parseInt(publicationIdParam));
		} catch (ServiceException e) {
			HttpSession session = request.getSession();
			session.setAttribute("serviceError", new Object());
			LOG.error("Exception has occurred on service layer", e);
			return new HttpPath(WebPath.Command.ADMIN_PUBLICATIONS_MANAGEMENT, HttpHandlerType.SEND_REDIRECT);
		}

		LOG.debug("Command finished");
		return new HttpPath(WebPath.Command.ADMIN_PUBLICATIONS_MANAGEMENT, HttpHandlerType.SEND_REDIRECT);
	}

}
