package com.a_smart_cookie.controller.command;

import com.a_smart_cookie.controller.route.HttpHandlerType;
import com.a_smart_cookie.controller.route.HttpPath;
import com.a_smart_cookie.controller.route.WebPath;
import com.a_smart_cookie.entity.User;
import com.a_smart_cookie.exception.ServiceException;
import com.a_smart_cookie.service.PaymentService;
import com.a_smart_cookie.service.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;

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

		HttpSession session = request.getSession(false);

		if (session == null || session.getAttribute("user") == null) {
			LOG.trace("No user in session");
			return new HttpPath(WebPath.Page.ERROR, HttpHandlerType.SEND_REDIRECT);
		}

		User user = (User) session.getAttribute("user");

		String publicationId = request.getParameter("item");

		if (publicationId == null) {
			LOG.trace("No publication id in request");
			return new HttpPath(WebPath.Page.ERROR, HttpHandlerType.SEND_REDIRECT);
		}

		try {
			PaymentService paymentService = ServiceFactory.getInstance().getPaymentService();
			Optional<User> updatedUser = paymentService.subscribeToPublication(user, Integer.parseInt(publicationId));

			if (updatedUser.isPresent()) {
				session.setAttribute("user", updatedUser.get());
				LOG.debug("Command finished");
				return new HttpPath(WebPath.Command.CATALOG_FIRST_PAGE, HttpHandlerType.SEND_REDIRECT);
			}

			LOG.error("Command finished without subscription");
			return new HttpPath(WebPath.Page.ERROR, HttpHandlerType.SEND_REDIRECT);

		} catch (ServiceException e) {
			LOG.error("Command finished with exception");
			return new HttpPath(WebPath.Page.ERROR, HttpHandlerType.SEND_REDIRECT);
		}
	}

}
