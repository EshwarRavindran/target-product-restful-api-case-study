package com.target.app.Controller;

import com.target.app.Exception.BadRequestException;
import com.target.app.Exception.ResourceNotFoundException;
import com.target.app.Model.ProductRequest;
import com.target.app.Model.ProductResponse;
import com.target.app.Service.ProductPriceServiceImpl;
import com.target.app.Utilities.RestInputValidator;
import com.target.app.Service.CassandraServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Spring Boot Rest Controller for the Product/Price API
 */
@RestController
@RequestMapping("/api/1")
public class ProductController {

    private static final Logger Log = LoggerFactory.getLogger(ProductController.class);

    private CassandraServiceImpl cassandraService;

    private ProductPriceServiceImpl productPriceService;

    private RestInputValidator restInputValidator;

    @Autowired
    public ProductController(ProductPriceServiceImpl productPriceService
            , CassandraServiceImpl cassandraService, RestInputValidator restInputValidator) {
        this.productPriceService = productPriceService;
        this.cassandraService = cassandraService;
        this.restInputValidator = restInputValidator;
    }


    /**
     * Get the ProductResponse by retrieving data from Cassandra and Redsky API
     * @param productId Input from URL and Unique for a product
     * @return ResponseEntity of {@link ProductResponse}
     * @throws ResourceNotFoundException
     */
    @GetMapping("/products/{id}")
    public ResponseEntity<ProductResponse> getProductWithPrice(@PathVariable("id") int productId) throws ResourceNotFoundException {

        ProductResponse productResponse = new ProductResponse();

            //Calling the external API to get product information
            productResponse = productPriceService.getProductResponse(productId);


        return ResponseEntity.ok(productResponse);
    }

    /**
     * Validate in request {@link ProductRequest} and update the product info in the DB
     * @param productRequest Input Json from Put request
     * @param productId Input from URL and Unique for a product
     * @return ResponseEntity of {@link ProductResponse}
     * @throws ResourceNotFoundException
     * @throws BadRequestException
     */
    @PutMapping(path = "/products/{id}", consumes = "application/json")
    public ResponseEntity<ProductResponse> updateProduct(@RequestBody ProductRequest productRequest,
                                                    @PathVariable("id") int productId)
                                                        throws ResourceNotFoundException, BadRequestException {
        Log.info("Put request "+ productRequest.toString() + "Product Id " + productId);

        ProductResponse productResponse = new ProductResponse();

        boolean isRequestValid = restInputValidator.isUpdateRequestValid(productRequest, productId);

        if(isRequestValid == Boolean.TRUE)
        {
            //Calling the DB service to update the product info
            productResponse = cassandraService.updateProductPriceInDatabase(productRequest);
            Log.info("Response after PUT " + productResponse.toString());
        }

        return ResponseEntity.ok(productResponse);

    }

}
