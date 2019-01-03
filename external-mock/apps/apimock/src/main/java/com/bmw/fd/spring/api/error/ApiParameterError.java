package com.bmw.fd.spring.api.error;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatus;

import java.util.List;

public class ApiParameterError extends ApiError {

    private final List<Parameter> parameterErrors;

    protected ApiParameterError(String logErrorId, String logMessage, List<Parameter> parameterErrors) {
        super(HttpStatus.BAD_REQUEST.value(), logErrorId, logMessage);
        this.parameterErrors = parameterErrors;
    }

    public List<Parameter> getParameterErrors() {
        return parameterErrors;
    }

    public static class Parameter {
        private String name;
        private String message;

        @JsonCreator
        protected Parameter(@JsonProperty("name") String name, @JsonProperty("message") String message) {
            this.name = name;
            this.message = message;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

}