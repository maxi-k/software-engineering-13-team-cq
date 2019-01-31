package com.bmw.fd.apimock.boundary.validation;

import com.atlassian.oai.validator.report.ValidationReport;
import com.bmw.fd.spring.api.error.ApiError;
import org.springframework.http.HttpStatus;

import java.util.List;

public class OpenApiError extends ApiError {

    private final List<ValidationReport.Message> validationMessages;

    protected OpenApiError(String logMessage, List<ValidationReport.Message> validationMessages) {
        super(HttpStatus.INTERNAL_SERVER_ERROR.value(), "BMWFD_OPEN_API_VALIDATION", logMessage);
        this.validationMessages = validationMessages;
    }

    public List<ValidationReport.Message> getValidationMessages() {
        return validationMessages;
    }
}
