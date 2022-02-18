package com.a_smart_cookie.controller.command;

import com.a_smart_cookie.controller.route.HttpHandlerType;
import com.a_smart_cookie.controller.route.HttpPath;
import com.a_smart_cookie.controller.route.WebPath;
import com.a_smart_cookie.entity.User;
import com.a_smart_cookie.entity.UserDetail;
import com.a_smart_cookie.exception.ServiceException;
import com.a_smart_cookie.service.PaymentService;
import com.a_smart_cookie.service.ServiceFactory;
import com.a_smart_cookie.util.payment.PaymentMethod;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Optional;

/**
 * Provides with performing payment.
 */
public class PaymentCommand extends Command {

	private static final long serialVersionUID = 7504430333129766667L;

	private static final Logger LOG = Logger.getLogger(PaymentCommand.class);

	@Override
	public HttpPath execute(HttpServletRequest request, HttpServletResponse response) {
		LOG.debug("Command starts");

		String paymentAmount = request.getParameter("paymentAmount");
		PaymentMethod paymentMethod = PaymentMethod.valueOf(request.getParameter("paymentMethod"));
		LOG.trace(paymentAmount + " " + paymentMethod);

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		LOG.trace("user --> " + user);

		try {
			PaymentService paymentService = ServiceFactory.getInstance().getPaymentService();
			Optional<BigDecimal> updatedBalance = paymentService.addBalanceToUserById(new BigDecimal(paymentAmount), paymentMethod, user.getId());

			if (updatedBalance.isPresent()) {
				user = User.UserBuilder.fromUser(user)
						.withUserDetail(new UserDetail(
								user.getUserDetail().getId(),
								user.getUserDetail().getFirstName(),
								user.getUserDetail().getFirstName(),
								updatedBalance.get()
						))
						.build();

				session.setAttribute("user", user);
				session.setAttribute("isCorrectPayment", true);
				return new HttpPath(WebPath.Command.CATALOG_FIRST_PAGE, HttpHandlerType.SEND_REDIRECT);
			}

			LOG.error("Command finished without updated balance");
			return new HttpPath(WebPath.Page.ERROR, HttpHandlerType.SEND_REDIRECT);

		} catch (ServiceException e) {
			LOG.error("Command finished with exception");
			return new HttpPath(WebPath.Page.ERROR, HttpHandlerType.SEND_REDIRECT);
		}
	}

}
