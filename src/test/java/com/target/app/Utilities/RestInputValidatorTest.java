package com.target.app.Utilities;

import com.target.app.Exception.BadRequestException;
import com.target.app.Model.Price;
import com.target.app.Model.ProductRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

/**
 * Test Class to test the {@link RestInputValidator} class
 */
@ExtendWith(MockitoExtension.class)
class RestInputValidatorTest {

    @InjectMocks
    private RestInputValidator restInputValidator;

    @Mock
    private ProductRequest productRequest;

    @Mock
    private Price price;

    @BeforeEach
    void setUp() {
        price = new Price(12.22, "USD");
        productRequest = new ProductRequest(1, "Name", price);
    }

    @Test
    @DisplayName("If all values are valid")
    void isUpdateRequestValidIfAllValuesAreCorrect() throws BadRequestException {


        boolean actual = restInputValidator.isUpdateRequestValid(productRequest, 1);

        assertEquals(true, actual);

    }

    @Test
    @DisplayName("If Product id in Path Variable does not match the product id in the Request")
    void isUpdateRequestValidIfProductIdIsNotMatching() throws BadRequestException {

        assertThrows(BadRequestException.class, () -> restInputValidator.isUpdateRequestValid(productRequest, 2));

    }




}