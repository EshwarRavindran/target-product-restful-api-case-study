package com.target.app.Model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Model class for PUT request to the API
 */
public class ProductRequest {

    @JsonProperty("id")
    private int productId;

    @JsonProperty("name")
    private String productName;

    @JsonProperty("current_price")
    private Price price;

    public ProductRequest(int productId, String productName, Price price) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
    }

    public ProductRequest() {
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "ProductRequest{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", price=" + price +
                '}';
    }
}
