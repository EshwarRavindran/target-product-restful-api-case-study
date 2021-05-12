package com.target.app.Service;

import com.target.app.Exception.ResourceNotFoundException;
import com.target.app.Model.Price;
import com.target.app.Model.ProductRequest;
import com.target.app.Model.ProductResponse;

public interface DatabaseService {


    /**
     *
     * @param productId
     * @return {@link Price}
     * @throws ResourceNotFoundException
     */
    public Price getPriceDetailsFromDataBase(int productId) throws ResourceNotFoundException;

    /**
     *
     * @param productRequest
     * @return {@link ProductResponse}
     * @throws ResourceNotFoundException
     */
    public ProductResponse updateProductPriceInDatabase(ProductRequest productRequest) throws ResourceNotFoundException;
}
