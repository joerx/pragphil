package io.yodo.pragphil.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class IllegalAccessException extends RuntimeException {

    public IllegalAccessException(String message) {
        super(message);
    }
}
