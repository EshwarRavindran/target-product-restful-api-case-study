package com.target.app.Utilities;

import com.target.app.Exception.BadRequestException;
import com.target.app.Model.ProductRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class RestInputValidator {

    private static final Logger Log = LoggerFactory.getLogger(RestInputValidator.class);
    private static final boolean IS_TRUE = true;

    public boolean isUpdateRequestValid(ProductRequest productRequest, int productId) throws BadRequestException {
        if (productRequest.getProductId() > 0 && productRequest.getProductId() == productId
            && !productRequest.getProductName().isEmpty() && productRequest.getPrice().getValue() > 0.0
            && !productRequest.getPrice().getCurrency_code().isEmpty())
        {
            return IS_TRUE;
        }
        else
        {
            Log.info("Put request body is not Valid");
            throw new BadRequestException("Put request body is not Valid");
        }
    }
}
