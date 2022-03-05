package com.a_smart_cookie.controller.command.subscriber;

import com.a_smart_cookie.controller.command.Command;
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
	public HttpPath execute(HttpServletRequest request, HttpServletResponse response) {
		LOG.debug("Command starts");

		String paymentAmountParam = request.getParameter("paymentAmount");
		String paymentMethodParam = request.getParameter("paymentMethod");

		HttpSession session = request.getSession();

		if (paymentAmountParam == null || paymentMethodParam == null) {
			LOG.error("Command finished with error --> parameters can't be null");
			session.setAttribute("illegalParams", new Object());
			return new HttpPath(WebPath.Command.CATALOG_FIRST_PAGE, HttpHandlerType.SEND_REDIRECT);
		}

		PaymentMethod paymentMethod = PaymentMethod.valueOf(paymentMethodParam);
		LOG.trace(paymentAmountParam + " " + paymentMethod);

		User user = (User) session.getAttribute("user");
		LOG.trace("user --> " + user);

		try {
			PaymentService paymentService = ServiceFactory.getInstance().getPaymentService();
			User updatedUser = paymentService.addBalanceToUser(new BigDecimal(paymentAmountParam), paymentMethod, user);
			session.setAttribute("user", updatedUser);
			session.setAttribute("isCorrectPayment", true);
			return new HttpPath(WebPath.Command.CATALOG_FIRST_PAGE, HttpHandlerType.SEND_REDIRECT);
		} catch (ServiceException serviceException) {
			session.setAttribute("isCorrectPayment", false);
			LOG.error("Exception has occurred", serviceException);
			return new HttpPath(WebPath.Command.CATALOG_FIRST_PAGE, HttpHandlerType.SEND_REDIRECT);
		} catch (NotUpdatedResultsException notUpdatedResultsException) {
			session.setAttribute("notUpdatedResult", new Object());
			LOG.error("Can't update results", notUpdatedResultsException);
			return new HttpPath(WebPath.Command.CATALOG_FIRST_PAGE, HttpHandlerType.SEND_REDIRECT);
		}
	}

}
