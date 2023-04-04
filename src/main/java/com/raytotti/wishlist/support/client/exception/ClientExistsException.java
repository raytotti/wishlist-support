package com.raytotti.wishlist.support.client.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Client.exists")
public class ClientExistsException extends RuntimeException {

}
