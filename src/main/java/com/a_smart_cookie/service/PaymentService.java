package com.a_smart_cookie.service;

import com.a_smart_cookie.entity.User;
import com.a_smart_cookie.exception.NotUpdatedResultsException;
import com.a_smart_cookie.exception.ServiceException;
import com.a_smart_cookie.util.payment.PaymentMethod;

import java.math.BigDecimal;

/**
 * Interface for creating concrete representation of PaymentService.
 *
 */
public interface PaymentService {

	/**
	 * Performs payment with depends on paymentMethod and adds balance to user.
	 *
	 * @param paymentAmount Requested number of money to be got from wallet.
	 * @param paymentMethod Method of getting money from wallet.
	 * @param user User to add money to.
	 * @return Returns updated user
	 * @throws ServiceException Occurred on dao layer
	 * @throws NotUpdatedResultsException Occurred when updated results can be got.
	 */
	User addBalanceToUser(BigDecimal paymentAmount, PaymentMethod paymentMethod, User user);

}
