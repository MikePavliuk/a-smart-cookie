package com.a_smart_cookie.controller.command.admin;

import com.a_smart_cookie.controller.command.Command;
import com.a_smart_cookie.controller.route.HttpHandlerType;
import com.a_smart_cookie.controller.route.HttpPath;
import com.a_smart_cookie.controller.route.WebPath;
import com.a_smart_cookie.entity.Status;
import com.a_smart_cookie.exception.ServiceException;
import com.a_smart_cookie.service.ServiceFactory;
import com.a_smart_cookie.service.UserService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Provides with ability to toggle user status
 *
 */
public class ChangeUserStatusCommand extends Command {

	private static final long serialVersionUID = -3823888060601773775L;

	private static final Logger LOG = Logger.getLogger(ChangeUserStatusCommand.class);

	@Override
	public HttpPath execute(HttpServletRequest request, HttpServletResponse response) {
		LOG.debug("Command starts");

		Status currentStatus = Status.valueOf(request.getParameter("status"));
		LOG.trace("status --> " + currentStatus);

		int userId = Integer.parseInt(request.getParameter("userId"));
		LOG.trace("userId --> " + userId);

		try {
			UserService userService = ServiceFactory.getInstance().getUserService();
			userService.changeUserStatus(userId, currentStatus);
		} catch (ServiceException e) {
			HttpSession session = request.getSession();
			session.setAttribute("serviceError", new Object());
			LOG.error("Exception has occurred on service layer", e);
			return new HttpPath(WebPath.Command.ADMIN_USERS, HttpHandlerType.SEND_REDIRECT);
		}


		LOG.debug("Command finished");
		return new HttpPath(WebPath.Command.ADMIN_USERS, HttpHandlerType.SEND_REDIRECT);
	}

}
