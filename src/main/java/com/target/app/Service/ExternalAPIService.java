package com.target.app.Service;

import com.target.app.Exception.ResourceNotFoundException;
import com.target.app.Model.ProductResponse;
import com.target.app.Model.RedskyProduct;
import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.CompletableFuture;

public interface ExternalAPIService {

    /**
     *
     * @param baseURl
     * @param productId
     * @param uriParams
     * @return {@link RedskyProduct} of type CompletableFuture
     * @throws ResourceNotFoundException
     */
    @Async
    public CompletableFuture<RedskyProduct> getRedskyResponse(String baseURl, int productId, String uriParams)
                                                        throws ResourceNotFoundException;
}
