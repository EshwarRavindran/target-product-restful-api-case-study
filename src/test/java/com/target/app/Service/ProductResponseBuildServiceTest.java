package com.target.app.Service;

import com.target.app.Exception.ResourceNotFoundException;
import com.target.app.Model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test Class for {@link ProductResponseBuildService} service
 */
@ExtendWith(MockitoExtension.class)
class ProductResponseBuildServiceTest {

    @Mock
    Price price;

    private ProductResponse productResponse;

    @Mock
    private Product product;

    @InjectMocks
    private ProductResponseBuildService productResponseBuildService;

    @BeforeEach
    void setUp() {
        price = new Price(12.11, "USD");
        product = new Product(new ProductItem(new ProductDescription("name")));
    }

    @Test
    @DisplayName("Product Response should be not null when Price and Product are valid")
    void buildProductResponseBodyWhenInputIsValid() throws ResourceNotFoundException {


        ProductResponse response = productResponseBuildService.buildProductResponseBody(price, product, 1);

        assertNotNull(response, "Product Response should not be null");

    }

    @Test
    @DisplayName("Product Response should be null when Price is null")
    void buildProductResponseBdy() throws ResourceNotFoundException {

        price = null;
        ProductResponse response = productResponseBuildService.buildProductResponseBody(price, product, 1);
        assertEquals(response.getProductId(),0, "Product id should not be more than zero");
        assertNull(response.getProductName(), "Product name should be null");
        assertNull(response.getPrice(), "Price should be null");

    }

}