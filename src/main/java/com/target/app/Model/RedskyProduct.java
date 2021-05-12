package com.target.app.Model;

/**
 * Model class to hold Redsky API response
 */
public class RedskyProduct {

    private Product product;

    public RedskyProduct(Product product) {
        this.product = product;
    }

    public RedskyProduct() {
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "RedskyProduct{" +
                "product=" + product +
                '}';
    }
}
