package com.a_smart_cookie.controller.command.subscriber;

import com.a_smart_cookie.controller.command.Command;
import com.a_smart_cookie.controller.route.HttpHandlerType;
import com.a_smart_cookie.controller.route.HttpPath;
import com.a_smart_cookie.controller.route.WebPath;
import com.a_smart_cookie.dto.user.SubscriptionWithPublicationInfo;
import com.a_smart_cookie.entity.Language;
import com.a_smart_cookie.entity.User;
import com.a_smart_cookie.service.ServiceFactory;
import com.a_smart_cookie.service.SubscriptionService;
import com.a_smart_cookie.util.CookieHandler;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Provides with subscriptions management for user.
 *
 */
public class SubscriptionsCommand extends Command {

	private static final long serialVersionUID = 5827816959811947668L;

	private static final Logger LOG = Logger.getLogger(SubscriptionsCommand.class);

	@Override
	public HttpPath execute(HttpServletRequest request, HttpServletResponse response) {
		LOG.debug("Command Starts");

		User user = (User) request.getSession().getAttribute("user");
		Language language = Language.safeFromString(CookieHandler.readCookieValue(request, "lang").orElse(Language.UKRAINIAN.getAbbr()));

		SubscriptionService subscriptionService = ServiceFactory.getInstance().getSubscriptionService();

		List<SubscriptionWithPublicationInfo> subscriptions = subscriptionService.getSubscriptionsWithFullInfoByUserAndLanguage(user, language);
		LOG.trace("subscriptions --> " + subscriptions);

		request.setAttribute("subscriptions", subscriptions);

		LOG.debug("Command finished");
		return new HttpPath(WebPath.Page.USER_SUBSCRIPTIONS, HttpHandlerType.FORWARD);
	}
}
