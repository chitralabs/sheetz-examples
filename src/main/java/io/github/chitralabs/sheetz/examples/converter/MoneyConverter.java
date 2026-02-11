package io.github.chitralabs.sheetz.examples.converter;

import io.github.chitralabs.sheetz.convert.ConvertContext;
import io.github.chitralabs.sheetz.convert.Converter;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Custom converter that handles monetary values with currency symbols.
 * Parses strings like "$1,234.56" or "1234.56" into BigDecimal.
 * Writes BigDecimal values as "$1,234.56" formatted strings.
 */
public class MoneyConverter implements Converter<BigDecimal> {

    @Override
    public BigDecimal fromCell(Object value, ConvertContext ctx) {
        if (value == null) return null;
        String s = value.toString().trim();
        if (s.isEmpty()) return null;

        // Remove currency symbols, commas, and whitespace
        s = s.replaceAll("[^\\d.\\-]", "");
        return new BigDecimal(s).setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public Object toCell(BigDecimal value) {
        if (value == null) return null;
        return "$" + String.format("%,.2f", value);
    }
}
