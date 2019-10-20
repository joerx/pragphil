package io.yodo.pragphil.api.endpoints;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class ErrorResponse {

    private String message;

    private String type;

    ErrorResponse(String message) {
        this.message = message;
    }

    ErrorResponse(Throwable t) {
        this.message = t.getMessage();
        this.type = t.getClass().getSimpleName();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
