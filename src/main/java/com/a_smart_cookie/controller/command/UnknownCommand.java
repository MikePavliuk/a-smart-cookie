package com.a_smart_cookie.controller.command;

import com.a_smart_cookie.controller.route.HttpHandlerType;
import com.a_smart_cookie.controller.route.HttpPath;
import com.a_smart_cookie.controller.route.WebPath;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UnknownCommand extends Command {

	private static final long serialVersionUID = 4594491816420609558L;

	@Override
	public HttpPath execute(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("errorMessage", "No such command");
		return new HttpPath(WebPath.Page.ERROR, HttpHandlerType.FORWARD);
	}

}
