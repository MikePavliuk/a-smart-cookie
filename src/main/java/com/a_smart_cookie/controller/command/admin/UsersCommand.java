package com.a_smart_cookie.controller.command.admin;

import com.a_smart_cookie.controller.command.Command;
import com.a_smart_cookie.controller.route.HttpHandlerType;
import com.a_smart_cookie.controller.route.HttpPath;
import com.a_smart_cookie.controller.route.WebPath;
import com.a_smart_cookie.exception.NotUpdatedResultsException;
import com.a_smart_cookie.exception.ServiceException;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Provides with information about users in the system.
 *
 */
public class UsersCommand extends Command {

	private static final long serialVersionUID = 8660826338889063524L;

	private static final Logger LOG = Logger.getLogger(UsersCommand.class);

	@Override
	public HttpPath execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException, NotUpdatedResultsException {
		return new HttpPath(WebPath.Page.ADMIN_USERS, HttpHandlerType.FORWARD);
	}
}
