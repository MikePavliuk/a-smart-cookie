package com.a_smart_cookie.controller.command.subscriber;

import com.a_smart_cookie.controller.command.Command;
import com.a_smart_cookie.controller.route.HttpHandlerType;
import com.a_smart_cookie.controller.route.HttpPath;
import com.a_smart_cookie.controller.route.WebPath;
import com.a_smart_cookie.entity.User;
import com.a_smart_cookie.exception.NotUpdatedResultsException;
import com.a_smart_cookie.exception.ServiceException;
import com.a_smart_cookie.service.ServiceFactory;
import com.a_smart_cookie.service.SubscriptionService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Provides with subscribing ability for user.
 *
 */
public class SubscribeCommand extends Command {

	private static final long serialVersionUID = -2241143544529213807L;

	private static final Logger LOG = Logger.getLogger(SubscribeCommand.class);

	@Override
	public HttpPath execute(HttpServletRequest request, HttpServletResponse response) {
		LOG.debug("Command starts");

		String publicationIdParam = request.getParameter("item");
		String periodParam = request.getParameter("period");

		if (publicationIdParam == null) {
			LOG.trace("No publication id in request");
			throw new IllegalArgumentException("Publication id param can't be null");
		}
		LOG.trace("Publication id --> " + publicationIdParam);

		if (periodParam == null) {
			LOG.trace("No period in request");
			throw new IllegalArgumentException("Period param can't be null");
		}

		int periodInMonths = Integer.parseInt(periodParam);
		LOG.trace("Period in months --> " + periodInMonths);

		if (periodInMonths <= 0 || periodInMonths > 12) {
			LOG.trace("Period is not valid.");
			throw new IllegalArgumentException("Period is not valid");
		}

		HttpSession session = request.getSession(false);
		User user = (User) session.getAttribute("user");

		try {
			SubscriptionService subscriptionService = ServiceFactory.getInstance().getSubscriptionService();
			User updatedUser = subscriptionService.subscribeToPublication(user, Integer.parseInt(publicationIdParam), periodInMonths);

			session.setAttribute("user", updatedUser);
			LOG.debug("Command finished");
			return new HttpPath(WebPath.Command.USER_SUBSCRIPTIONS, HttpHandlerType.SEND_REDIRECT);

		} catch (ServiceException | NotUpdatedResultsException e) {
			session.invalidate();
			throw e;
		}
	}

}
