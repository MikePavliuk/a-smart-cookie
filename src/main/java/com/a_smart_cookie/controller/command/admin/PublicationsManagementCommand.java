package com.a_smart_cookie.controller.command.admin;

import com.a_smart_cookie.controller.command.Command;
import com.a_smart_cookie.controller.route.HttpHandlerType;
import com.a_smart_cookie.controller.route.HttpPath;
import com.a_smart_cookie.controller.route.WebPath;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Provides with CRUD operation page on publications for admin.
 *
 */
public class PublicationsManagementCommand extends Command {

	private static final long serialVersionUID = -2137507939125502082L;

	private static final Logger LOG = Logger.getLogger(PublicationsManagementCommand.class);

	@Override
	public HttpPath execute(HttpServletRequest request, HttpServletResponse response) {
		LOG.debug("Command starts");

		LOG.debug("Command finished");
		return new HttpPath(WebPath.Page.ADMIN_PUBLICATIONS, HttpHandlerType.FORWARD);
	}

}
