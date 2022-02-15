package com.a_smart_cookie.controller;

import com.a_smart_cookie.controller.command.Command;
import com.a_smart_cookie.controller.command.CommandContext;
import com.a_smart_cookie.controller.route.HttpPath;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Main servlet controller, which implements Front controller pattern.
 *
 */
@WebServlet("/controller")
public class FrontController extends HttpServlet {

	private static final long serialVersionUID = 2883474034122955977L;

	private static final Logger LOG = Logger.getLogger(FrontController.class);

	@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		LOG.debug("Front controller doGet starts");
		HttpPath httpPath = processRequest(req, resp);
		LOG.debug("Front controller doGet finished, now go to address with handling type --> "
				+ httpPath.getPath() + " - " + httpPath.getHttpHandlerType());

		switch (httpPath.getHttpHandlerType()) {
			case FORWARD:
				RequestDispatcher disp = req.getRequestDispatcher(httpPath.getPath().getValue());
				disp.forward(req, resp);
				break;

			case SEND_REDIRECT:
				resp.sendRedirect(req.getContextPath() + httpPath.getPath().getValue());
				break;
		}
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		LOG.debug("Front controller doPost starts");
		HttpPath httpPath = processRequest(req, resp);
		LOG.debug("Front controller doPost finished, now sendRedirect to --> " + httpPath.getPath());

		resp.sendRedirect(req.getContextPath() + httpPath.getPath().getValue());
    }

	/**
	 * Main method of this controller, that process all: Get and Post, - requests.
	 *
	 */
	private HttpPath processRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		// extract command name from the request
		String commandName = req.getParameter("command");
		LOG.trace("Request parameter: command --> " + commandName);

		// obtain command object by its name
		Command command = CommandContext.getCommand(commandName);
		LOG.trace("Obtained command --> " + command);

		// execute command and get http handler type with path
		HttpPath httpPath = command.execute(req, resp);
		LOG.trace("HttpPath --> " + httpPath);
		return httpPath;
	}

}
