package com.a_smart_cookie.controller.command.guest;

import com.a_smart_cookie.controller.command.Command;
import com.a_smart_cookie.controller.route.HttpHandlerType;
import com.a_smart_cookie.controller.route.HttpPath;
import com.a_smart_cookie.controller.route.WebPath;
import com.a_smart_cookie.dao.EntityColumn;
import com.a_smart_cookie.dto.user.UserSignUpDto;
import com.a_smart_cookie.entity.User;
import com.a_smart_cookie.service.ServiceFactory;
import com.a_smart_cookie.service.UserService;
import com.a_smart_cookie.util.validation.UserValidator;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Provides with sign up mechanism for user.
 *
 */
public class RegistrationCommand extends Command {

	private static final long serialVersionUID = -4209296784400138731L;

	private static final Logger LOG = Logger.getLogger(RegistrationCommand.class);

	@Override
	public HttpPath execute(HttpServletRequest request, HttpServletResponse response) {
		LOG.debug("Command starts");

		HttpSession session = request.getSession();

		UserSignUpDto userSignUpDto = new UserSignUpDto(
				request.getParameter("firstName"),
				request.getParameter("lastName"),
				request.getParameter("email"),
				request.getParameter("password")
		);
		LOG.trace("userSingUpDto --> " + userSignUpDto);

		HttpPath notValidHttpPath = performValidationMechanism(request, session, userSignUpDto);
		if (notValidHttpPath != null) return notValidHttpPath;

		LOG.trace("User is valid");

		UserService userService = ServiceFactory.getInstance().getUserService();

		if (!userService.isUserAlreadyExistsByEmail(userSignUpDto.getEmail())) {
			session.invalidate();
			User user = userService.createNewUser(userSignUpDto);
			LOG.trace("user --> " + user);

			session = request.getSession();
			session.setAttribute("registeredEmail", user.getEmail());

			LOG.debug("Command finished with registered user");
			return new HttpPath(WebPath.Command.SIGN_IN, HttpHandlerType.SEND_REDIRECT);
		}

		session.setAttribute("emailAlreadyExists", true);
		addOldFieldValuesToSession(request, session);
		LOG.debug("Command finished with existed user email");
		return new HttpPath(WebPath.Command.SIGN_UP, HttpHandlerType.SEND_REDIRECT);
	}

	private HttpPath performValidationMechanism(HttpServletRequest request, HttpSession session, UserSignUpDto userSignUpDto) {
		Map<String, Boolean> validationResult = UserValidator.getValidationResults(userSignUpDto);

		if (validationResult.containsValue(false)) {
			session.setAttribute("isValidName", validationResult.get(EntityColumn.UserDetail.NAME.getName()));
			session.setAttribute("isValidSurname", validationResult.get(EntityColumn.UserDetail.SURNAME.getName()));
			session.setAttribute("isValidEmail", validationResult.get(EntityColumn.User.EMAIL.getName()));
			session.setAttribute("isValidPassword", validationResult.get(EntityColumn.User.PASSWORD.getName()));

			addOldFieldValuesToSession(request, session);

			LOG.debug("Command finished with not valid user");
			return new HttpPath(WebPath.Page.SIGN_UP, HttpHandlerType.SEND_REDIRECT);
		}
		return null;
	}

	private void addOldFieldValuesToSession(HttpServletRequest request, HttpSession session) {
		session.setAttribute("oldFirstName", request.getParameter("firstName"));
		session.setAttribute("oldLastName", request.getParameter("lastName"));
		session.setAttribute("oldSignUpEmail", request.getParameter("email"));
	}
}
