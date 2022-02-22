package com.a_smart_cookie.controller.command.admin;

import com.a_smart_cookie.controller.command.Command;
import com.a_smart_cookie.controller.route.HttpHandlerType;
import com.a_smart_cookie.controller.route.HttpPath;
import com.a_smart_cookie.controller.route.WebPath;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
			LOG.trace("No publication id in request");
			throw new IllegalArgumentException("Publication id param can't be null");
		}

		LOG.debug("Command finished");
		return new HttpPath(WebPath.Page.ADMIN_PUBLICATION_EDIT, HttpHandlerType.FORWARD);
	}

}
