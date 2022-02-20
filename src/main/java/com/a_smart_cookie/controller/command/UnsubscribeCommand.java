package com.a_smart_cookie.controller.command;

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
 * Provides with unsubscribing ability for user.
 *
 */
public class UnsubscribeCommand extends Command {

	private static final long serialVersionUID = 4127843576162580803L;

	private static final Logger LOG = Logger.getLogger(UnsubscribeCommand.class);

	@Override
	public HttpPath execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException, NotUpdatedResultsException {
		LOG.debug("Command starts");

		String publicationIdParam = request.getParameter("item");

		if (publicationIdParam == null) {
			LOG.trace("No publication id in request");
			throw new IllegalArgumentException("Publication id param can't be null");
		}

		HttpSession session = request.getSession(false);
		User user = (User) session.getAttribute("user");

		try {
			SubscriptionService subscriptionService = ServiceFactory.getInstance().getSubscriptionService();
			User updatedUser = subscriptionService.unsubscribeFromPublication(user, Integer.parseInt(publicationIdParam));

			session.setAttribute("user", updatedUser);
			LOG.debug("Command finished");
			return new HttpPath(WebPath.Command.USER_SUBSCRIPTIONS, HttpHandlerType.SEND_REDIRECT);

		} catch (ServiceException | NotUpdatedResultsException e) {
			session.invalidate();
			throw e;
		}
	}
}
