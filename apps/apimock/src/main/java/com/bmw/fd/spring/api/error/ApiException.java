package com.bmw.fd.spring.api.error;

public class ApiException extends RuntimeException {

    private final ApiError error;

    public ApiException(ApiError error) {
        super(error.getLogMessage());
        this.error = error;
    }

    public ApiError getError() {
        return error;
    }
}
