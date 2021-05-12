package com.target.app.Service;

import com.target.app.Exception.ResourceNotFoundException;
import com.target.app.Model.*;
import com.target.app.Repository.ProductDetailsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.cassandra.CassandraConnectionFailureException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

/**
 * Test class for the {@link CassandraServiceImpl} service
 */
@ExtendWith(MockitoExtension.class)
class CassandraServiceImplTest {

    @Mock
    private ProductDetailsRepository productDetailsRepository;

    @Mock
    private ProductDetailsData productDetailsData;

    @Mock
    private Price price;

    @Mock
    private Product product;

    @Mock
    private ProductRequest productRequest;

    @Mock
    private ProductResponseBuildService productResponseBuildService;

    private ProductResponse productResponse;



    @InjectMocks
    private CassandraServiceImpl cassandraService;

    @BeforeEach
    void setUp() {
        price = new Price(12.11, "USD");
        productRequest = new ProductRequest(1, "Name", price);
        product = new Product(new ProductItem(new ProductDescription("name")));
    }

    @Test
    @DisplayName("Product details is found in the database for GET Request")
    void priceDetailsFoundInDataBaseForGet() throws ResourceNotFoundException {

        when(productDetailsRepository.findById(anyInt())).thenReturn(Optional.of(productDetailsData));

        Price price = cassandraService.getPriceDetailsFromDataBase(1);

        assertNotNull(price, "Price should not be null");
    }

    @Test
    @DisplayName("Cassandra DB connection fails on GET Request")
    void whenDBConnectionFailsOnGet() {

        doThrow(CassandraConnectionFailureException.class).when(productDetailsRepository).findById(anyInt());
        assertThrows(CassandraConnectionFailureException.class,
                () -> cassandraService.getPriceDetailsFromDataBase(1)
                , "CassandraConnectionFailureException error must be thrown on connection error");
    }

    @Test
    @DisplayName("Product price details are not found in the DB for GET Request")
    void priceDetailsNotFoundInDataBaseOnGet() throws ResourceNotFoundException {

        when(productDetailsRepository.findById(anyInt())).thenReturn(null);
        assertThrows(ResourceNotFoundException.class,
               () -> cassandraService.getPriceDetailsFromDataBase(1),
              "ResourceNotFoundException must be thrown when matching product details is not found in the DB");
 }

    @Test
    @Disabled
    void updateProductPriceInDatabase() throws ResourceNotFoundException {

        doAnswer(invocationOnMock ->  {
                return new ProductResponse(1, "name" , new Price(11.22, "USD"));
            })
                .when(productResponseBuildService).buildProductResponseBody(any(Price.class), any(Product.class) , anyInt());

        when(productDetailsRepository.findById(anyInt())).thenReturn(Optional.of(productDetailsData));

        when(productDetailsRepository.save(productDetailsData)).thenReturn(productDetailsData);
ProductResponse response = cassandraService.updateProductPriceInDatabase(productRequest);

        assertNotNull(response);
    }

    @Test
    @DisplayName("Product details is found in the database for Put Request")
    @Disabled
    void priceDetailsFoundInDataBaseForPut() throws ResourceNotFoundException {

        when(productDetailsRepository.findById(anyInt())).thenReturn(Optional.of(productDetailsData));
        when(productDetailsRepository.save(productDetailsData)).thenReturn(productDetailsData);
        when(productResponseBuildService.buildProductResponseBody(any(), any(), anyInt()))
                                                                .thenReturn(productResponse);

        ProductResponse productResponse = cassandraService.updateProductPriceInDatabase(productRequest);

        assertNotNull(productResponse, "Product Response should not be null");
    }

    @Test
    @DisplayName("Cassandra DB connection fails on PUT Request")
    void whenDBConnectionFailsOnPut() {

        doThrow(CassandraConnectionFailureException.class).when(productDetailsRepository).findById(anyInt());
        assertThrows(CassandraConnectionFailureException.class,
                () -> cassandraService.updateProductPriceInDatabase(productRequest),
               "CassandraConnectionFailureException error must be thrown on connection error");
    }

    @Test
    @DisplayName("Product price details are not found in the DB for PUT Request")
    void priceDetailsNotFoundInDataBaseOnPut() throws ResourceNotFoundException {

        when(productDetailsRepository.findById(anyInt())).thenReturn(null);
        assertThrows(ResourceNotFoundException.class,
                            () -> cassandraService.updateProductPriceInDatabase(productRequest),
                "ResourceNotFoundException must be thrown when matching product details is not found in the DB");
    }
}