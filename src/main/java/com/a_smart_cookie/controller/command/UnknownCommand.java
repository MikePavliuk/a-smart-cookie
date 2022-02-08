package com.a_smart_cookie.controller.command;

import com.a_smart_cookie.controller.WebPath;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UnknownCommand extends Command {

	private static final long serialVersionUID = 4594491816420609558L;

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("errorMessage", "No such command");
		return WebPath.Page.ERROR.getValue();
	}

}
