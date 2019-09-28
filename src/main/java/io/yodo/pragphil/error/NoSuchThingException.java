package io.yodo.pragphil.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NoSuchThingException extends RuntimeException {
    public NoSuchThingException(String message) {
        super(message);
    }
}
