package bzu.student;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Nested
@DisplayName("CurrencyConversionRateTest")
class CurrencyConversionRateTest {
    @Test
    @DisplayName("Test Basic Conversion Rate")
    void testBasicConversion() throws SQLException {
        TaskOne taskOne = new TaskOne();
        taskOne.connect();
        CurrencyConversionRate currencyConversionRate = new CurrencyConversionRate();
        currencyConversionRate.setFromCurrency("USD");
        currencyConversionRate.setToCurrency("EUR");
        currencyConversionRate.setRate(taskOne.getConversionRate("USD", "EUR"));
        double amount = 100*currencyConversionRate.getRate();
        taskOne.close();
        assertEquals(amount, currencyConversionRate.convert(100));
    }

    @Test
    @DisplayName("Test Inverse Conversion Rate")
    void testInverseConversion() throws SQLException {
        TaskOne taskOne = new TaskOne();
        taskOne.connect();
        CurrencyConversionRate currencyConversionRate = new CurrencyConversionRate();
        currencyConversionRate.setFromCurrency("EUR");
        currencyConversionRate.setToCurrency("USD");
        currencyConversionRate.setRate(taskOne.getConversionRate("EUR", "USD"));
        double amount = 100*currencyConversionRate.getRate();
        taskOne.close();
        assertEquals(amount, currencyConversionRate.convert(100));
    }

    @Test
    @DisplayName("Test Same Currency Conversion Rate")
    void testSameCurrencyConversion() throws SQLException {
        TaskOne taskOne = new TaskOne();
        taskOne.connect();
        CurrencyConversionRate currencyConversionRate = new CurrencyConversionRate();
        currencyConversionRate.setFromCurrency("USD");
        currencyConversionRate.setToCurrency("USD");
        currencyConversionRate.setRate(taskOne.getConversionRate("USD", "USD"));
        double amount = 100;
        taskOne.close();
        assertEquals(amount, currencyConversionRate.convert(100));
    }

    @Test
    @DisplayName("Test Zero Amount Conversion Rate")
    void testZeroAmountConversion() throws SQLException {
        TaskOne taskOne = new TaskOne();
        taskOne.connect();
        CurrencyConversionRate currencyConversionRate = new CurrencyConversionRate();
        currencyConversionRate.setFromCurrency("GBP");
        currencyConversionRate.setToCurrency("JPY");
        currencyConversionRate.setRate(taskOne.getConversionRate("GBP", "JPY"));
        double amount = 0*currencyConversionRate.getRate();
        taskOne.close();
        assertEquals(-1, currencyConversionRate.convert(0));
    }

    @Test
    @DisplayName("Test Large Amount Conversion Rate")
    void testLargeAmountConversion() throws SQLException {
        TaskOne taskOne = new TaskOne();
        taskOne.connect();
        CurrencyConversionRate currencyConversionRate = new CurrencyConversionRate();
        currencyConversionRate.setFromCurrency("CAD");
        currencyConversionRate.setToCurrency("AUD");
        currencyConversionRate.setRate(taskOne.getConversionRate("CAD", "AUD"));
        double amount = 1000000*currencyConversionRate.getRate();
        taskOne.close();
        assertEquals(amount, currencyConversionRate.convert(1000000));
    }

    @Test
    @DisplayName("Test Nonexistent Source Currency")
    void testNonexistentSourceCurrency() throws SQLException {
        TaskOne taskOne = new TaskOne();
        taskOne.connect();
        CurrencyConversionRate currencyConversionRate = new CurrencyConversionRate();
        currencyConversionRate.setFromCurrency("XYZ");
        currencyConversionRate.setToCurrency("EUR");
        currencyConversionRate.setRate(taskOne.getConversionRate("XYZ", "EUR"));
        double amount = -1;
        taskOne.close();
        assertEquals(amount, currencyConversionRate.convert(amount));
    }

    @Test
    @DisplayName("Test Nonexistent Target Currency")
    void testNonexistentTargetCurrency() throws SQLException {
        TaskOne taskOne = new TaskOne();
        taskOne.connect();
        CurrencyConversionRate currencyConversionRate = new CurrencyConversionRate();
        currencyConversionRate.setFromCurrency("USD");
        currencyConversionRate.setToCurrency("XYZ");
        currencyConversionRate.setRate(taskOne.getConversionRate("USD", "XYZ"));
        System.out.println(currencyConversionRate.getRate());
        double amount = -1;
        taskOne.close();
        assertEquals(amount, currencyConversionRate.convert(amount));
    }

    @Test
    @DisplayName("Test Empty Input")
    void testEmptyInput() throws SQLException {
        TaskOne taskOne = new TaskOne();
        taskOne.connect();
        CurrencyConversionRate currencyConversionRate = new CurrencyConversionRate();
        currencyConversionRate.setFromCurrency("");
        currencyConversionRate.setToCurrency("USD");
        currencyConversionRate.setRate(taskOne.getConversionRate("", "USD"));
        double amount = -1;
        taskOne.close();
        assertEquals(amount, currencyConversionRate.convert(100));
    }

    @Test
    @DisplayName("Test Decimal Amount Conversion Rate")
    void testDecimalAmountConversion() throws SQLException {
        TaskOne taskOne = new TaskOne();
        taskOne.connect();
        CurrencyConversionRate currencyConversionRate = new CurrencyConversionRate();
        currencyConversionRate.setFromCurrency("JPY");
        currencyConversionRate.setToCurrency("USD");
        currencyConversionRate.setRate(taskOne.getConversionRate("JPY", "USD"));
        double amount = 123.45*currencyConversionRate.getRate();
        taskOne.close();
        assertEquals(amount, currencyConversionRate.convert(123.45));
    }

    @Test
    @DisplayName("Test Negative Amount Conversion Rate")
    void testNegativeAmountConversion() throws SQLException {
        TaskOne taskOne = new TaskOne();
        taskOne.connect();
        CurrencyConversionRate currencyConversionRate = new CurrencyConversionRate();
        currencyConversionRate.setFromCurrency("GBP");
        currencyConversionRate.setToCurrency("CAD");
        currencyConversionRate.setRate(taskOne.getConversionRate("GBP", "CAD"));
        double amount = -1;
        taskOne.close();
        assertEquals(amount, currencyConversionRate.convert(-50));
    }






}