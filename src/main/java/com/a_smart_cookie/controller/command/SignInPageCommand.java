package com.a_smart_cookie.controller.command;

import com.a_smart_cookie.controller.route.HttpHandlerType;
import com.a_smart_cookie.controller.route.HttpPath;
import com.a_smart_cookie.controller.route.WebPath;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SignInPageCommand extends Command {

	private static final long serialVersionUID = -7839366578058993131L;

	private static final Logger LOG = Logger.getLogger(SignInPageCommand.class);

	@Override
	public HttpPath execute(HttpServletRequest request, HttpServletResponse response) {
		LOG.debug("Command starts");

		LOG.debug("Command finished");
		return new HttpPath(WebPath.Page.SIGN_IN, HttpHandlerType.FORWARD);
	}
}
