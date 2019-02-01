package com.bmw.fd.apimock.boundary.validation;

import com.atlassian.oai.validator.springmvc.InvalidResponseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class OpenApiExceptionHandler {

    @ExceptionHandler(InvalidResponseException.class)
    public ResponseEntity<Object> handleException(InvalidResponseException ex) {
        String message = ex.getValidationReport().getMessages().isEmpty() ? ex.getMessage() :
                ex.getValidationReport().getMessages().get(0).getMessage();
        OpenApiError error = new OpenApiError(message, ex.getValidationReport().getMessages());
        return ResponseEntity.status(error.getStatusCode()).body(error);
    }
}
