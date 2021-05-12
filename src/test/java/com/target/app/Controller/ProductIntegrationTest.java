package com.target.app.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.target.app.Model.ProductRequest;
import com.target.app.Model.ProductResponse;
import com.target.app.Service.CassandraServiceImpl;
import com.target.app.Service.ProductPriceServiceImpl;
import com.target.app.TargetProductApiApplication;
import com.target.app.Utilities.RestInputValidator;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;


import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;

import static io.restassured.RestAssured.given;

/**
 * Integration test class for {@link RestController} controller
 */

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {TargetProductApiApplication.class,
        ProductController.class, ProductResponse.class, ProductPriceServiceImpl.class, ProductRequest.class,
        CassandraServiceImpl.class, RestInputValidator.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProductIntegrationTest {

    @LocalServerPort
    private int port;

    private ExtractableResponse<Response> responseProduct;

    private static final String PRODUCT_ID_PARAM = "id";

    private static final int ID = 13860428;
    private static final int BAD_ID = 604;

    private static final String NAME = "name";

    @BeforeAll
    public void setup() {

        RestAssured.port = this.port;
        RestAssured.defaultParser = Parser.JSON;

    }

    @Test
    @DisplayName("Integration Test for GET request with valid Path Variable")
    void getProductWhenMatchingProductIsFound() throws Exception {

        String uri = "/api/1/products/{id}";

        given().that().with().pathParam(PRODUCT_ID_PARAM, ID).when().get(uri).then().assertThat()
                .statusCode(HttpStatus.OK.value());
    }


    @Test
    @DisplayName("Integration Test for PUT request with valid RequestBody")
    void putRequestToDBWhenInPutRequestIsValid() throws IOException {

        String uri = "/api/1/products/{id}";
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream( "upsert_product.json");
        ProductRequest productRequest = new ObjectMapper().readValue(inputStream, ProductRequest.class);
        String jsonString = new ObjectMapper().writeValueAsString(productRequest);

        given().port(port).when().with().pathParam(PRODUCT_ID_PARAM, ID).contentType("application/json").body(jsonString).put(uri).then()
                .assertThat().statusCode(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("Integration Test for PUT request with invalid RequestBody")
    void httpNotFoundOnPutRequestWhenInPutRequestIsValid() throws IOException {

        String uri = "/api/1/products/{id}";
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream( "upsert_product_bad.json");
        ProductRequest productRequest = new ObjectMapper().readValue(inputStream, ProductRequest.class);
        String jsonString = new ObjectMapper().writeValueAsString(productRequest);

        given().port(port).when().with().pathParam(PRODUCT_ID_PARAM, BAD_ID).contentType("application/json").body(jsonString).put(uri).then()
                .assertThat().statusCode(HttpStatus.NOT_FOUND.value());
    }


}