package com.target.app.Model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Model class for Product node of Redsky API
 */
public class Product {

    @JsonProperty(value = "item")
    private ProductItem productItem;

    public Product(ProductItem productItem) {
        this.productItem = productItem;
    }

    public Product() {
    }

    public ProductItem getProductItem() {
        return productItem;
    }

    public void setProductItem(ProductItem productItem) {
        this.productItem = productItem;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productItem=" + productItem +
                '}';
    }
}
