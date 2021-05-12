package com.target.app.Service;

import com.target.app.Exception.ResourceNotFoundException;
import com.target.app.Model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Class to call external API and DB to pull data
 */
@Service
public class ProductPriceServiceImpl {

    private static final Logger Log = LoggerFactory.getLogger(ProductPriceServiceImpl.class);

    @Value("${redsky.URL.base}")
    private String redskrUrlBase;

    @Value("${redsky.URL.uri}")
    private String redskrUrlUri;

    private ProductResponseBuildService productResponseBuildService;
    private ExternalAPIService redskyService;
    private CassandraServiceImpl cassandraService;


    @Autowired
    public ProductPriceServiceImpl(ProductResponseBuildService productResponseBuildService
            , CassandraServiceImpl cassandraService, ExternalAPIService redskyService) {
        this.productResponseBuildService = productResponseBuildService;
        this.cassandraService = cassandraService;
        this.redskyService = redskyService;
    }

    /**
     * Calls the Redsky API to get {@link Product} information
     * Calls the Cassandra DB to {@link Price} information
     * Calls the response builder class to construct the API response
     * @param productId Input from URL and Unique for a product
     * @return {@link ProductResponse} Model for creating the API response
     * @throws ResourceNotFoundException
     */
    public ProductResponse getProductResponse(int productId)throws ResourceNotFoundException
    {
        Product productFromRedsky = null;
        ProductResponse productResponse = null;
        Price price = null;
        
        try {
             CompletableFuture<RedskyProduct> redskyProductCompletableFuture = null;

            //Get CompletableFuture type response from Redsky Service
            redskyProductCompletableFuture =  redskyService.getRedskyResponse(redskrUrlBase, productId, redskrUrlUri);
            RedskyProduct responseFromRedsky = redskyProductCompletableFuture.get();
            Log.info("Response from Redsky "+responseFromRedsky.toString());

            //Get Price information from DB service
            price = cassandraService.getPriceDetailsFromDataBase(productId);
            Log.info("Price from Cassandra Service " + price.toString());

            //Get Product information from Redsky response
            productFromRedsky = responseFromRedsky.getProduct();

            //Calling API response build service to construct API response
            productResponse = productResponseBuildService.buildProductResponseBody(price, productFromRedsky, productId);

        }
        catch (ExecutionException e) {
           Log.error("Redsky Exception" + e.getMessage());
        } catch (InterruptedException e) {
            Log.error("Redsky Exception" + e.getMessage());
        }

        return productResponse;
    }


}
