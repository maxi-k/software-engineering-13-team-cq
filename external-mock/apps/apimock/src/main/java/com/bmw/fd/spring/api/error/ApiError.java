package com.bmw.fd.spring.api.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

// see https://atc.bmwgroup.net/bitbucket/projects/MRP/repos/micro-service-reference-project/browse/war/src/main/java/com/bmw/cc/mrp/api/error/v1/entity
public class ApiError {

    private final int statusCode;
    private final String requestUrl = ServletUriComponentsBuilder.fromCurrentRequest().build().toString();
    private final String logTransactionId = UUID.randomUUID().toString(); // TODO set correlation id from logging
    private final String logErrorId;
    private final String logMessage;
    private final Long requestTimestamp = System.currentTimeMillis(); // TODO take from request if given


    protected ApiError(int statusCode, String logErrorId, String logMessage) {
        this.statusCode = statusCode;
        this.logErrorId = logErrorId;
        this.logMessage = logMessage;
    }

    public static ApiError error(HttpStatus status, Enum errorId, String message) {
        return new ApiError(status.value(), errorId.name(), message);
    }

    public static ApiParameterError error(Enum errorId, String message, List<ApiParameterError.Parameter> parameterErrors) {
        return new ApiParameterError(errorId.name(), message, parameterErrors);
    }

    public static ApiParameterError error(Enum errorId, String message, ApiParameterError.Parameter... parameterErrors) {
        return new ApiParameterError(errorId.name(), message, Arrays.asList(parameterErrors));
    }

    public static ApiParameterError.Parameter parameter(String name, String message) {
        return new ApiParameterError.Parameter(name, message);
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public String getLogTransactionId() {
        return logTransactionId;
    }

    public String getLogErrorId() {
        return logErrorId;
    }

    public String getLogMessage() {
        return logMessage;
    }

    public Long getRequestTimestamp() {
        return requestTimestamp;
    }
}