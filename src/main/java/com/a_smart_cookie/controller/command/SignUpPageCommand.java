package com.a_smart_cookie.controller.command;

import com.a_smart_cookie.controller.route.HttpHandlerType;
import com.a_smart_cookie.controller.route.HttpPath;
import com.a_smart_cookie.controller.route.WebPath;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class SignUpPageCommand extends Command {

	private static final long serialVersionUID = -6062947839872352840L;

	private static final Logger LOG = Logger.getLogger(SignUpPageCommand.class);

	@Override
	public HttpPath execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		LOG.debug("Command starts");

		LOG.debug("Command finished");
		return new HttpPath(WebPath.Page.SIGN_UP, HttpHandlerType.FORWARD);
	}

}
