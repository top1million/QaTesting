package bzu.student;

public class CurrencyConversionRate {

    private String fromCurrency;

    private String toCurrency;

    private double rate;


    public String getFromCurrency() {
        return fromCurrency;
    }

    public void setFromCurrency(String fromCurrency) {
        this.fromCurrency = fromCurrency;
    }

    public String getToCurrency() {
        return toCurrency;
    }

    public void setToCurrency(String toCurrency) {
        this.toCurrency = toCurrency;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
    public double convert(double amount){
        if (amount <= 0){
            return -1;
        }
        if (fromCurrency.length() == 0 || toCurrency.length() == 0){
            return -1;
        }
        return amount * rate;
    }
}
