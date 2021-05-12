package com.target.app.Model;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

/**
 * Model class for Cassandra DB table information
 */
@Table(value = "Product_Details")
public class ProductDetailsData {

    @PrimaryKey(value = "productid")
    private int productId;

    @Column(value = "value")
    private double value;

    @Column(value = "currency_code")
    private String currency_code;

    public ProductDetailsData(int productId, double value, String currency_code) {
        this.productId = productId;
        this.value = value;
        this.currency_code = currency_code;
    }

    public ProductDetailsData() {
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
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
        return "ProductDetailsData{" +
                "productId=" + productId +
                ", value=" + value +
                ", currency_code='" + currency_code + '\'' +
                '}';
    }
}
