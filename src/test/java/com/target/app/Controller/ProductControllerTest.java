package com.target.app.Controller;

import com.target.app.Exception.BadRequestException;
import com.target.app.Exception.ResourceNotFoundException;
import com.target.app.Model.Price;
import com.target.app.Model.ProductRequest;
import com.target.app.Model.ProductResponse;
import com.target.app.Service.CassandraServiceImpl;
import com.target.app.Service.ProductPriceServiceImpl;
import com.target.app.Utilities.RestInputValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

/**
 * Test class for {@link ProductController} Controller
 */
@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    private ProductPriceServiceImpl productPriceService;

    @Mock
    private ProductResponse productResponse;

    @Mock
    private Price price;

    @Mock
    private ProductRequest productRequest;

    @Mock
    private RestInputValidator restInputValidator;

    @Mock
    private CassandraServiceImpl cassandraService;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    void setUp() {

        price = new Price(12.2, "USD");
        productRequest = new ProductRequest(1, "Name", price);

    }

    @Test
    @DisplayName("HttpResponse.ok when the matching product for the product id is found for GET Request")
    void getProductWhenMatchingProductIsFound() throws ResourceNotFoundException {

        when(productPriceService.getProductResponse(anyInt())).thenReturn(productResponse);

        ResponseEntity<ProductResponse> responseEntity =  productController.getProductWithPrice(1);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode(), "HttpStatus.Ok must be returned");

    }

    @Test
    @DisplayName("HttpResponse.NOT_FOUND when the matching product for the product id is not found for GET Request")
    void throwExceptionOnGetWhenMatchingProductIsNotFound() throws ResourceNotFoundException {
        doThrow(ResourceNotFoundException.class).when(productPriceService).getProductResponse(anyInt());
        assertThrows(ResourceNotFoundException.class,
                () -> productController.getProductWithPrice(1),
                "HttpStatus.NOT_FOUND must be returned");
    }

    @Test
    @DisplayName("HttpResponse.ok when the matching product for the product id is found for PUT Request")
    void updateProductWhenMatchingProductIsFound() throws ResourceNotFoundException, BadRequestException {

        when(cassandraService.updateProductPriceInDatabase(productRequest)).thenReturn(productResponse);

        when(restInputValidator.isUpdateRequestValid(any(), anyInt())).thenReturn(true);

        ResponseEntity<ProductResponse> responseEntity =  productController.updateProduct(productRequest, 1);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode(), "HttpStatus.Ok must be returned");

    }

    @Test
    @DisplayName("HttpResponse.NOT_FOUND when the matching product for the product id is not found for PUT Request")
    void throwExceptionOnUpdateWhenMatchingProductIsNotFound() throws  BadRequestException {

        doThrow(BadRequestException.class).when(restInputValidator).isUpdateRequestValid(any(), anyInt());

        assertThrows(BadRequestException.class,
                () -> productController.updateProduct(productRequest, 1),
                "HttpStatus.NOT_FOUND must be returned");

    }
}