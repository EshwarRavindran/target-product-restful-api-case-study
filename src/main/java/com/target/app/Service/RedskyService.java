package com.target.app.Service;

import com.target.app.Exception.ResourceNotFoundException;
import com.target.app.Model.RedskyProduct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

/**
 * Class to get product information from external Redsky API
 */
@Service
public class RedskyService implements ExternalAPIService {


    private RestTemplate restTemplate;

    private static final Logger Log = LoggerFactory.getLogger(RedskyService.class);

    @Autowired
    public RedskyService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Makes an Async the Redsky API with constructed URL and gets Response Entity of type {@link RedskyProduct}
     * @param baseURl Base URL for Redsky API
     * @param productId Input from URL and Unique for a product
     * @param uriParams URI for Redsky API
     * @return {@link RedskyProduct} of type CompletableFuture
     * @throws ResourceNotFoundException
     */
    @Override
    public CompletableFuture<RedskyProduct> getRedskyResponse(String baseURl, int productId, String uriParams)
            throws ResourceNotFoundException {

        RedskyProduct redskyProduct = new RedskyProduct();
        ResponseEntity<RedskyProduct> responseEntity = null;

        try {
            responseEntity = restTemplate.getForEntity(baseURl+productId+uriParams, RedskyProduct.class);
            redskyProduct = responseEntity.getBody();
            Log.info("Redsky Product from API call " + redskyProduct.toString());

        }catch (HttpClientErrorException httpClientErrorException)
        {
            if(httpClientErrorException.getStatusCode().equals(HttpStatus.NOT_FOUND))
            {
                Log.info("Product Id " + productId + "is not found in Redsky");
                throw new ResourceNotFoundException("Product Id " + productId + " is not found in Redsky");
            }
            else
            {
                Log.info("Redsky API call failed");
                throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Redsky API call failed");
            }
        }

        return CompletableFuture.completedFuture(redskyProduct);
    }
}
