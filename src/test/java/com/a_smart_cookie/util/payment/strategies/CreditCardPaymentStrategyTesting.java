package com.a_smart_cookie.util.payment.strategies;

import com.a_smart_cookie.exception.PaymentException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreditCardPaymentStrategyTesting {

    private final CreditCardPaymentStrategy creditCardPayment = new CreditCardPaymentStrategy();

    @Test
    void givenNull_whenPay_thenThrowPaymentException() {
        assertThrows(PaymentException.class,
                () -> creditCardPayment.pay(null));
    }

    @ParameterizedTest
    @MethodSource("provideNotPositiveBigDecimals")
    void givenNotPositiveBigDecimalInputValues_whenPay_thenThrowPaymentException(BigDecimal notPositiveValues) {
        assertThrows(PaymentException.class,
                () -> creditCardPayment.pay(notPositiveValues));
    }

    @Test
    void givenPositiveBigDecimalInputValues_whenPay_thenReturnTrue() throws PaymentException {
        assertTrue(creditCardPayment.pay(BigDecimal.valueOf(0.99)));
    }

    private static Stream<Arguments> provideNotPositiveBigDecimals() {
        return Stream.of(
                Arguments.of(BigDecimal.ZERO),
                Arguments.of(BigDecimal.valueOf(-100))
        );
    }

}
