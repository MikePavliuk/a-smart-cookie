package com.a_smart_cookie.controller.command;

import com.a_smart_cookie.controller.route.HttpHandlerType;
import com.a_smart_cookie.controller.route.HttpPath;
import com.a_smart_cookie.controller.route.WebPath;
import com.a_smart_cookie.dao.EntityColumn;
import com.a_smart_cookie.entity.User;
import com.a_smart_cookie.exception.HashingException;
import com.a_smart_cookie.exception.ServiceException;
import com.a_smart_cookie.service.ServiceFactory;
import com.a_smart_cookie.service.UserService;
import com.a_smart_cookie.util.hashing.PBKDF2Hash;
import com.a_smart_cookie.util.validation.UserValidator;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.Optional;

/**
 * Provides with sign in mechanism for user.
 *
 */
public class LoginCommand extends Command {

	private static final long serialVersionUID = -6045416586328449454L;

	private static final Logger LOG = Logger.getLogger(CatalogCommand.class);

	@Override
	public HttpPath execute(HttpServletRequest request, HttpServletResponse response) {
		LOG.debug("Command starts");

		String email = request.getParameter("email");
		String password = request.getParameter("password");

		Map<String, Boolean> validationResult = UserValidator.getValidationResults(email, password);

		HttpSession session = request.getSession();

		if (validationResult.containsValue(false)) {
			session.setAttribute("isValidEmail", validationResult.get(EntityColumn.User.EMAIL.getName()));
			session.setAttribute("isValidPassword", validationResult.get(EntityColumn.User.PASSWORD.getName()));

			LOG.debug("Command finished with not valid user");
			return new HttpPath(WebPath.Page.SIGN_IN, HttpHandlerType.SEND_REDIRECT);
		}

		LOG.trace("User is valid");

		try {
			UserService userService = ServiceFactory.getInstance().getUserService();

			Optional<User> user = userService.getUserWithoutSubscriptionsByEmail(email);

			if (user.isEmpty()) {
				session.setAttribute("badCredentials", true);
				setOldEmailToSession(email, session);
				LOG.debug("Command finished with not found user");
				return new HttpPath(WebPath.Page.SIGN_IN, HttpHandlerType.SEND_REDIRECT);
			}

			if (!PBKDF2Hash.verifyHash(password, user.get().getSalt(), user.get().getPassword())) {
				session.setAttribute("badCredentials", true);
				setOldEmailToSession(email, session);
				LOG.debug("Command finished with not equals passwords");
				return new HttpPath(WebPath.Page.SIGN_IN, HttpHandlerType.SEND_REDIRECT);
			}

			if (user.get().getStatus() == User.Status.BLOCKED) {
				session.setAttribute("isBlocked",true);
				setOldEmailToSession(email, session);
				LOG.debug("Command finished with blocked user");
				return new HttpPath(WebPath.Page.SIGN_IN, HttpHandlerType.SEND_REDIRECT);
			}

			session.invalidate();
			session = request.getSession();
			session.setAttribute("loggedUser", user);

			LOG.debug("Command finished with signed in user");
			return new HttpPath(WebPath.Command.CATALOG_FIRST_PAGE, HttpHandlerType.SEND_REDIRECT);

		} catch (ServiceException | HashingException e) {
			LOG.error("Command ended with exception");
			return new HttpPath(WebPath.Page.ERROR, HttpHandlerType.SEND_REDIRECT);
		}
	}

	private void setOldEmailToSession(String email, HttpSession session) {
		session.setAttribute("oldEmail", email);
	}

}
