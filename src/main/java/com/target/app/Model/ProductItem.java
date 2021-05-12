package com.target.app.Model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Model class for Item node of Redsky API
 */
public class ProductItem {

    @JsonProperty(value = "product_description")
    private ProductDescription productDescription;

    public ProductItem(ProductDescription productDescription) {
        this.productDescription = productDescription;
    }

    public ProductItem() {
    }

    public ProductDescription getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(ProductDescription productDescription) {
        this.productDescription = productDescription;
    }

    @Override
    public String toString() {
        return "ProductItem{" +
                "productDescription=" + productDescription +
                '}';
    }
}
