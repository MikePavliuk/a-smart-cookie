package com.a_smart_cookie.controller.command;

import com.a_smart_cookie.controller.route.HttpHandlerType;
import com.a_smart_cookie.controller.route.HttpPath;
import com.a_smart_cookie.controller.route.WebPath;
import com.a_smart_cookie.dao.EntityColumn;
import com.a_smart_cookie.dto.catalog.UserSignUpDto;
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
public class SignUpCommand extends Command {

	private static final long serialVersionUID = -4209296784400138731L;

	private static final Logger LOG = Logger.getLogger(SignUpCommand.class);

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

		Map<String, Boolean> validationResults = UserValidator.getValidationResults(userSignUpDto);

		validationResults.forEach((key, value) -> System.out.println(key + " " + value));

		if (!validationResults.containsValue(false)) {
			LOG.debug("Command finished with valid user");
			return new HttpPath(
					WebPath.Page.SIGN_IN,
					HttpHandlerType.SEND_REDIRECT
			);
		}

		session.setAttribute("isValidName", validationResults.get(EntityColumn.UserDetail.NAME.getName()));
		session.setAttribute("isValidSurname", validationResults.get(EntityColumn.UserDetail.SURNAME.getName()));
		session.setAttribute("isValidEmail", validationResults.get(EntityColumn.User.EMAIL.getName()));
		session.setAttribute("isValidPassword", validationResults.get(EntityColumn.User.PASSWORD.getName()));

		session.setAttribute("oldFirstName", request.getParameter("firstName"));
		session.setAttribute("oldLastName", request.getParameter("lastName"));
		session.setAttribute("oldEmail", request.getParameter("email"));

		LOG.debug("Command finished with not valid user");
		return new HttpPath(
				WebPath.Page.SIGN_UP,
				HttpHandlerType.SEND_REDIRECT
		);
	}
}
