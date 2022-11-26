package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FixedRatesProviderTest {
    private FixedRatesProvider rates;

    @BeforeEach
    void setUp(){
        rates = new FixedRatesProvider();
    }

    @Test
    void shouldReturnKnowRate(){
        rates.setRate(Currency.EUR, Currency.PLN, new BigDecimal("4.7"));
        assertEquals(rates.getRate(Currency.EUR, Currency.PLN), new BigDecimal("4.7000"));
    }

    @Test
    void shouldReturnReversedRate(){
        rates.setRate(Currency.EUR, Currency.PLN, new BigDecimal("4.7"));
        assertEquals(rates.getRate(Currency.PLN, Currency.EUR), new BigDecimal("0.2128"));

    }

    @Test
    void shouldThrowExceptionOnIllegalRatePair(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> rates.getRate(Currency.EUR, Currency.PLN));
    }

}
