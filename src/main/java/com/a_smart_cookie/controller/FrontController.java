package com.a_smart_cookie.controller;

import com.a_smart_cookie.controller.command.Command;
import com.a_smart_cookie.controller.command.CommandContext;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/controller")
public class FrontController extends HttpServlet {

	private static final long serialVersionUID = 2883474034122955977L;

	@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
    }

	private void process(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

		// extract command name from the request
		String commandName = req.getParameter("command");

		// obtain command object by its name
		Command command = CommandContext.getCommand(commandName);

		// execute command and get forward address
		String forward = command.execute(req, resp);

		// if the forward address is not null go to the address
		if (forward != null) {
			RequestDispatcher disp = req.getRequestDispatcher(forward);
			disp.forward(req, resp);
		}

	}

}
