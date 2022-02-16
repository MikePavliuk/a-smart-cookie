package com.a_smart_cookie.controller.command;

import com.a_smart_cookie.controller.route.HttpHandlerType;
import com.a_smart_cookie.controller.route.HttpPath;
import com.a_smart_cookie.controller.route.WebPath;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Performs logging user out.
 * Removes user from session.
 *
 */
public class LogoutCommand extends Command {

	private static final long serialVersionUID = 7611911486070908011L;

	private static final Logger log = Logger.getLogger(LogoutCommand.class);

	@Override
	public HttpPath execute(HttpServletRequest request, HttpServletResponse response) {
		log.debug("Command starts");

		HttpSession session = request.getSession(false);

		if (session != null) {
			session.invalidate();
		}

		log.debug("Command finished");
		return new HttpPath(WebPath.Page.SIGN_IN, HttpHandlerType.SEND_REDIRECT);
	}

}
