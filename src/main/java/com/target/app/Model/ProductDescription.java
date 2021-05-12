package com.target.app.Model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Model class for Description node of Redsky API
 */
public class ProductDescription {

    @JsonProperty(value = "title")
    private String productTitle;

    public ProductDescription(String productTitle) {
        this.productTitle = productTitle;
    }

    public ProductDescription() {
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    @Override
    public String toString() {
        return "ProductDescription{" +
                "productTitle='" + productTitle + '\'' +
                '}';
    }
}
