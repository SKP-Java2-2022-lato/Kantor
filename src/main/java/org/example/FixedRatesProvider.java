package org.example;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class FixedRatesProvider implements RatesProvider{

    private static final int SCALE = 4;
    private static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_EVEN;

    private static class ExchangePair{
        private Currency from;
        private Currency to;

        public ExchangePair(Currency from, Currency to) {
            this.from = from;
            this.to = to;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ExchangePair that = (ExchangePair) o;
            return from == that.from && to == that.to;
        }

        @Override
        public int hashCode() {
            return Objects.hash(from, to);
        }
    }

    //notowania
    private final Map<ExchangePair, BigDecimal> rates = new HashMap<>();


    @Override
    public BigDecimal getRate(Currency from, Currency to) {
       ExchangePair pair = new ExchangePair(from, to);
       if(!rates.containsKey(pair))
           throw new IllegalArgumentException(String.format("Brak notowania dla pary %s, %s", from, to));
       return rates.get(pair);
    }

    public void setRate(Currency from, Currency to, BigDecimal rate){
        rates.put(new ExchangePair(from,to), rate.setScale(SCALE, ROUNDING_MODE));
        rates.put(new ExchangePair(to, from), BigDecimal.ONE.divide(rate,SCALE, ROUNDING_MODE));

    }
}
