package com.a_smart_cookie.filter;

import com.a_smart_cookie.controller.route.WebPath;
import com.a_smart_cookie.entity.Role;
import com.a_smart_cookie.entity.User;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

/**
 * Security filter. Disabled by default.
 * Uncomment Security filter section in web.xml to enable.
 *
 */
public class CommandAccessFilter implements Filter {

	private static final Logger LOG = Logger.getLogger(CommandAccessFilter.class);

	// commands access	
	private static final Map<Role, List<String>> accessMap = new HashMap<>();
	private static List<String> commons = new ArrayList<>();

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		LOG.debug("Filter starts");

		if (accessAllowed(request)) {
			LOG.debug("Filter finished");
			chain.doFilter(request, response);
		} else {
			HttpServletRequest req = (HttpServletRequest) request;
			HttpServletResponse resp = (HttpServletResponse) response;

			String errorMessage = "You do not have permission to access the requested resource";

			request.setAttribute("errorMessage", errorMessage);
			LOG.trace("Set the request attribute: errorMessage --> " + errorMessage);

			resp.sendRedirect(req.getContextPath() + WebPath.Command.ERROR.getValue());
		}
	}

	private boolean accessAllowed(ServletRequest request) {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String commandName = request.getParameter("command");

		if (commandName == null || commandName.isEmpty()) {
			return false;
		}

		HttpSession session = httpRequest.getSession(false);

		Role role = null;

		if (session != null && session.getAttribute("user") != null) {
			role = ((User) session.getAttribute("user")).getRole();
		}

		return commons.contains(commandName) || accessMap.get(role).contains(commandName);
	}

	public void init(FilterConfig fConfig) {
		LOG.debug("Filter initialization starts");

		// roles
		accessMap.put(Role.ADMIN, asList(fConfig.getInitParameter("admin")));
		accessMap.put(Role.SUBSCRIBER, asList(fConfig.getInitParameter("subscriber")));
		accessMap.put(null, asList(fConfig.getInitParameter("guest")));
		LOG.trace("Access map --> " + accessMap);

		// commons
		commons = asList(fConfig.getInitParameter("common"));
		LOG.trace("Common commands --> " + commons);

		LOG.debug("Filter initialization finished");
	}

	/**
	 * Extracts parameter values from string.
	 *
	 * @param str parameter values string.
	 * @return list of parameter values.
	 */
	private List<String> asList(String str) {
		List<String> list = new ArrayList<>();
		StringTokenizer st = new StringTokenizer(str);
		while (st.hasMoreTokens()) list.add(st.nextToken());
		return list;
	}

}