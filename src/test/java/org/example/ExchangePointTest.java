package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExchangePointTest {

    private FixedRatesProvider fixedRatesProvider;

    @BeforeEach
    void setUp(){
        fixedRatesProvider = new FixedRatesProvider();
        fixedRatesProvider.setRate(Currency.EUR, Currency.PLN, new BigDecimal("4.7"));
        fixedRatesProvider.setRate(Currency.USD, Currency.PLN, new BigDecimal("5.0"));
    }

    @Test
    void shouldntAcceptNegativeSpread(){
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new ExchangePoint(fixedRatesProvider, new BigDecimal("-1")));
    }

    @Test
    void shouldntAcceptSpreadBiggerThan1(){
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new ExchangePoint(fixedRatesProvider, new BigDecimal("10")));
    }

    @Test
    void shouldntAcceptSpreadEqual1(){
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new ExchangePoint(fixedRatesProvider, BigDecimal.ONE));
    }

    @Test
    void shouldReturnBidPriceForCurrencyAfterSpread(){
        ExchangePoint exchangePoint = new ExchangePoint(fixedRatesProvider, new BigDecimal("0.05"));
        assertEquals(exchangePoint.getBidPrice(new BigDecimal("10"), Currency.PLN, Currency.EUR), new BigDecimal("2.0216"));
    }

    @Test
    void shouldReturnAskPriceForCurrencyAfterSpread(){
        ExchangePoint exchangePoint = new ExchangePoint(fixedRatesProvider, new BigDecimal("0.05"));
        assertEquals(exchangePoint.getAskPrice(new BigDecimal("10"), Currency.EUR, Currency.PLN), new BigDecimal("49.3500"));
    }
}
