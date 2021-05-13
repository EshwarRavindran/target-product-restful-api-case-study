package com.target.app.Service;

import com.target.app.Exception.ResourceNotFoundException;
import com.target.app.Model.Price;
import com.target.app.Model.Product;
import com.target.app.Model.ProductResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Class to build the response sent back by the API
 */
@Service
public class ProductResponseBuildService {

    private static final Logger Log = LoggerFactory.getLogger(ProductResponseBuildService.class);

    /**
     * Method builds the API response for {@link com.target.app.Controller.ProductController}
     * @param price {@link Price} Price model class
     * @param product {@link Product} Primary node structure Model for Redsky response
     * @param productId Input from URL and Unique for a product
     * @return {@link ProductResponse} Model for creating the API response
     *
     */
    public ProductResponse buildProductResponseBody(Price price, Product product, int productId) throws ResourceNotFoundException {
        ProductResponse productResponse = new ProductResponse();

        if(product != null)
        {
            if(product.getProductItem() != null && product.getProductItem().getProductDescription() != null &&
                    product.getProductItem().getProductDescription().getProductTitle() != null && price != null)
            {
                productResponse.setPrice(price);
                productResponse.setProductId(productId);
                productResponse.setProductName(product
                        .getProductItem().getProductDescription().getProductTitle());
            }
            Log.info("Built product response " + productResponse.toString());
        }
        else
        {
            Log.error("Product Id " + productId + " is not found in Redsky");
            throw new ResourceNotFoundException("Product Id " + productId + " is not found in Redsky");
        }


        return productResponse;
    }
}
