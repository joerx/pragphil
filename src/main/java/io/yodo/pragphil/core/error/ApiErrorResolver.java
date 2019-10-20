package io.yodo.pragphil.core.error;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class ApiErrorResolver {

    private final Logger log = LoggerFactory.getLogger(getClass());

    public void setErrorResponse(HttpStatus status, HttpServletResponse response, Throwable ex) {
        try {
            response.setStatus(status.value());
            response.setContentType("application/json");

            ApiError apiError = new ApiError(ex.getMessage());
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(apiError);

            response.getWriter().write(json);
        } catch (IOException e) {
            log.error("Error handling Filter error", e);
        }
    }

    static class ApiError {
        public String message;
        ApiError(String message) {
            this.message = message;
        }
    }
}
