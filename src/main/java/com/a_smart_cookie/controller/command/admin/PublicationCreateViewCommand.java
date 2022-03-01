package com.a_smart_cookie.controller.command.admin;

import com.a_smart_cookie.controller.command.Command;
import com.a_smart_cookie.controller.route.HttpHandlerType;
import com.a_smart_cookie.controller.route.HttpPath;
import com.a_smart_cookie.controller.route.WebPath;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Provides with forwarding to page for creating  publication.
 *
 */
public class PublicationCreateViewCommand extends Command {

	private static final long serialVersionUID = 8093008677476374870L;

	private static final Logger LOG = Logger.getLogger(PublicationCreateViewCommand.class);

	@Override
	public HttpPath execute(HttpServletRequest request, HttpServletResponse response) {
		LOG.debug("Command starts");

		LOG.debug("Command finished");
		return new HttpPath(WebPath.Page.ADMIN_PUBLICATION_CREATE, HttpHandlerType.FORWARD);
	}

}
