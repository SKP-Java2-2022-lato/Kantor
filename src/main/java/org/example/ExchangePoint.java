package org.example;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ExchangePoint {

    private static final int SCALE = 4;
    private static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_EVEN;

    private final RatesProvider ratesProvider;
    private final BigDecimal askSpread;
    private final BigDecimal bidSpread;

    public ExchangePoint(RatesProvider ratesProvider, BigDecimal spread){
        if(spread.compareTo(BigDecimal.ZERO) < 0){
            throw  new IllegalArgumentException(String.format("Spread %s jest niepoprawny", spread.toString()));
        }
        if(spread.compareTo(BigDecimal.ONE) >= 0){
            throw  new IllegalArgumentException(String.format("Spread %s jest niepoprawny", spread.toString()));
        }
        this.ratesProvider = ratesProvider;
        this.askSpread = BigDecimal.ONE.add(spread).setScale(SCALE, ROUNDING_MODE);
        this.bidSpread = BigDecimal.ONE.subtract(spread).setScale(SCALE, ROUNDING_MODE);
    }

    public BigDecimal getBidPrice(BigDecimal amount, Currency from, Currency to){
        return getExchangePrice(amount, from, to, bidSpread);
    }

    public BigDecimal getAskPrice(BigDecimal amount, Currency from, Currency to){
        return getExchangePrice(amount,from, to, askSpread);
    }

    private BigDecimal getExchangePrice(BigDecimal amount, Currency from, Currency to, BigDecimal spread){
        BigDecimal rate = ratesProvider.getRate(from, to);
        BigDecimal amountInCurrency = rate.multiply(amount);
        return amountInCurrency.multiply(spread).setScale(SCALE, ROUNDING_MODE);
    }

}
