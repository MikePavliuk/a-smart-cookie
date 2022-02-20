package com.a_smart_cookie.util.payment;

import com.a_smart_cookie.exception.PaymentException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PaymentContextTesting {

    @Mock
    private PaymentStrategy paymentStrategy;
    private PaymentContext paymentContext;

    @BeforeEach
    void initContextWithMock() {
        paymentContext = new PaymentContext(paymentStrategy);
    }


    @Test
    void givenNull_whenInitializingStrategyInConstructor_thenThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> new PaymentContext(null));
    }

    @Test
    void givenAnyPaymentAmount_whenPerformingFailedPayment_thenReturnFalse() throws PaymentException {
        when(paymentStrategy.pay(any())).thenReturn(false);

        assertFalse(paymentContext.performPayment(any()));

        verify(paymentStrategy, times(1)).pay(any());
    }

    @Test
    void givenAnyPaymentAmount_whenPerformingCorrectPayment_thenReturnTrue() throws PaymentException {
        when(paymentStrategy.pay(any())).thenReturn(true);

        assertTrue(paymentContext.performPayment(any()));

        verify(paymentStrategy, times(1)).pay(any());
    }

}
