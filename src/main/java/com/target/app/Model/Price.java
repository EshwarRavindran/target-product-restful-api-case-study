package com.target.app.Model;

/**
 * Model class to hold price information
 */
public class Price {

    private double value;
    private String currency_code;

    public Price(double value, String currency_code) {
        this.value = value;
        this.currency_code = currency_code;
    }

    public Price() {
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getCurrency_code() {
        return currency_code;
    }

    public void setCurrency_code(String currency_code) {
        this.currency_code = currency_code;
    }

    @Override
    public String toString() {
        return "Price{" +
                "value=" + value +
                ", currency_code='" + currency_code + '\'' +
                '}';
    }
}
