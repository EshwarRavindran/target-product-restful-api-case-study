package com.target.app.Service;

import com.target.app.Exception.ResourceNotFoundException;
import com.target.app.Model.*;
import com.target.app.Repository.ProductDetailsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.CassandraConnectionFailureException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service class to implement all Cassandra transactions
 */
@Service
public class CassandraServiceImpl {

    private static final Logger Log = LoggerFactory.getLogger(CassandraServiceImpl.class);

    private ProductDetailsRepository productDetailsRepository;

    private ProductResponseBuildService productResponseBuildService;

    @Autowired
    public CassandraServiceImpl(ProductDetailsRepository productDetailsRepository
            , ProductResponseBuildService productResponseBuildService) {
        this.productDetailsRepository = productDetailsRepository;
        this.productResponseBuildService = productResponseBuildService;
    }

    /**
     * Gets the product details from the DB and extracts the price
     * @param productId Input from URL and Unique for a product
     * @return {@link Price} Model for holding product price information
     * @throws ResourceNotFoundException
     */
    public Price getPriceDetailsFromDataBase(int productId) throws ResourceNotFoundException {

            Optional<ProductDetailsData> productDetailsData = null;
            Price price = new Price();

            try{
                //Gets ProductDetailsData from Repository using productId
                productDetailsData = productDetailsRepository.findById(productId);
                if(productDetailsData != null && productDetailsData.isPresent())
                {
                    //Set Price value from ProductDetailsData
                    price.setValue(productDetailsData.get().getValue());
                    price.setCurrency_code(productDetailsData.get().getCurrency_code());
                    Log.info("Price details from DB " + price.toString());
                }
                else
                {
                    Log.info("Product with id " + productId + " is not found in the DB");
                    throw new ResourceNotFoundException("Product with id "
                            + productId + " is not found in the DB");
                }

            }catch (CassandraConnectionFailureException exception)
            {
                Log.info(exception.getMessage());
                throw exception;
            }

            return price;

    }

    /**
     * Gets the request from the PUT rest call and checks if product exists in the DB
     * If it does then updates it with the {@link Price} from the request body
     * @param productRequest Input Json from Put request
     * @return {@link ProductResponse} Model for creating the API response
     * @throws ResourceNotFoundException
     */
    public ProductResponse updateProductPriceInDatabase(ProductRequest productRequest) throws ResourceNotFoundException {
        ProductDetailsData productDetailsData = new ProductDetailsData();
        //ProductDetailsData updatedData = new ProductDetailsData();
        Optional<ProductDetailsData> checkIfProductExists = null;
        Price price = null;

        try {
            //Check if Product with productId exists in the DB
            checkIfProductExists = productDetailsRepository
                    .findById(productRequest.getProductId());
            if(checkIfProductExists != null && checkIfProductExists.isPresent())
            {
                //Set product details to the ProductDetailsData to save in the DB
                productDetailsData.setCurrency_code(productRequest.getPrice().getCurrency_code());
                productDetailsData.setValue(productRequest.getPrice().getValue());
                productDetailsData.setProductId(productRequest.getProductId());

                //Save ProductDetailsData to DB
                productDetailsData = productDetailsRepository.save(productDetailsData);
            }
            else
            {
                Log.info("Resource cannot be updated. Product with id "
                        + productRequest.getProductId() + " is not found in the DB");
                throw new ResourceNotFoundException("Resource cannot be updated. Product with id "
                        + productRequest.getProductId() + " is not found in the DB");
            }

        }catch (CassandraConnectionFailureException exception)
        {
            Log.info(exception.getMessage());
            throw exception;
        }

        if(productDetailsData !=null && productDetailsData.getValue() > 0.0
                && productDetailsData.getCurrency_code() != null)
        {
            price = new Price(productDetailsData.getValue(), productDetailsData.getCurrency_code());
            Log.info("Updated Price Details " + price.toString());
        }
        Product product = new Product(new ProductItem(new ProductDescription(productRequest.getProductName())));
        Log.info("Updated Product Details " + product.toString());

        return productResponseBuildService.buildProductResponseBody(price, product, productRequest.getProductId());
    }
}
