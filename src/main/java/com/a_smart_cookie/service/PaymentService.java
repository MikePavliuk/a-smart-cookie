package com.a_smart_cookie.service;

import com.a_smart_cookie.exception.ServiceException;
import com.a_smart_cookie.util.payment.PaymentMethod;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Interface for creating concrete representation of PaymentService.
 *
 */
public interface PaymentService {

	/**
	 * Performs payment with depends on paymentMethod.
	 *
	 * @param paymentAmount Requested number of money to be got from wallet.
	 * @param paymentMethod Method of getting money from wallet.
	 * @param userId Id of user to add money.
	 * @return If transaction was correctly performed - returns current balance, otherwise - empty optional.
	 */
	Optional<BigDecimal> addBalanceToUserById(BigDecimal paymentAmount, PaymentMethod paymentMethod, int userId) throws ServiceException;

}
