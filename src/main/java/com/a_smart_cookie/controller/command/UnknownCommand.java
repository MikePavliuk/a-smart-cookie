package com.a_smart_cookie.controller.command;

import com.a_smart_cookie.controller.route.HttpHandlerType;
import com.a_smart_cookie.controller.route.HttpPath;
import com.a_smart_cookie.controller.route.WebPath;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Serves situation when requested command is not exist.
 *
 */
public class UnknownCommand extends Command {

	private static final long serialVersionUID = 4594491816420609558L;

	private static final Logger LOG = Logger.getLogger(UnknownCommand.class);

	@Override
	public HttpPath execute(HttpServletRequest request, HttpServletResponse response) {
		LOG.debug("Commands starts");

		request.setAttribute("errorMessage", "No such command");

		LOG.debug("Commands finished");
		return new HttpPath(WebPath.Page.ERROR, HttpHandlerType.FORWARD);
	}

}
