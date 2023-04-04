package com.raytotti.wishlist.support.product.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Product.notFound")
public class ProductNotFoundException extends RuntimeException {

}
