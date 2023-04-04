package com.raytotti.wishlist.support.client.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Client.notFound")
public class ClientNotFoundException extends RuntimeException {

}
