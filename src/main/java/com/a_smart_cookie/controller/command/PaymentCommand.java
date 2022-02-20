package com.a_smart_cookie.controller.command;

import com.a_smart_cookie.controller.route.HttpHandlerType;
import com.a_smart_cookie.controller.route.HttpPath;
import com.a_smart_cookie.controller.route.WebPath;
import com.a_smart_cookie.entity.User;
import com.a_smart_cookie.exception.NotUpdatedResultsException;
import com.a_smart_cookie.exception.ServiceException;
import com.a_smart_cookie.service.PaymentService;
import com.a_smart_cookie.service.ServiceFactory;
import com.a_smart_cookie.util.payment.PaymentMethod;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;

/**
 * Provides with performing payment.
 */
public class PaymentCommand extends Command {

	private static final long serialVersionUID = 7504430333129766667L;

	private static final Logger LOG = Logger.getLogger(PaymentCommand.class);

	@Override
	public HttpPath execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException, NotUpdatedResultsException {
		LOG.debug("Command starts");

		String paymentAmountParam = request.getParameter("paymentAmount");
		String paymentMethodParam = request.getParameter("paymentMethod");

		if (paymentAmountParam == null || paymentMethodParam == null) {
			LOG.error("Command finished with error --> paymentAmount == null");
			throw new IllegalArgumentException("Payment amount can't be null");
		}

		PaymentMethod paymentMethod = PaymentMethod.valueOf(paymentMethodParam);
		LOG.trace(paymentAmountParam + " " + paymentMethod);

		HttpSession session = request.getSession(false);
		User user = (User) session.getAttribute("user");
		LOG.trace("user --> " + user);

		PaymentService paymentService = ServiceFactory.getInstance().getPaymentService();
		try {
			User updatedUser = paymentService.addBalanceToUser(new BigDecimal(paymentAmountParam), paymentMethod, user);
			session.setAttribute("user", updatedUser);
			session.setAttribute("isCorrectPayment", true);
			return new HttpPath(WebPath.Command.CATALOG_FIRST_PAGE, HttpHandlerType.SEND_REDIRECT);
		} catch (ServiceException | NotUpdatedResultsException e) {
			session.invalidate();
			throw e;
		}
	}

}
