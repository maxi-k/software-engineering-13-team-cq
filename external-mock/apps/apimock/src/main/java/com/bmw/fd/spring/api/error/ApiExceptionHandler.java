package com.bmw.fd.spring.api.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.util.WebUtils;

import javax.validation.ConstraintViolationException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiExceptionHandler.class);

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<Object> handleException(ApiException ex) {
        return handleError(ex.getError());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleException(ConstraintViolationException ex) {
        List<ApiParameterError.Parameter> parameterErrors = ex.getConstraintViolations().stream()
                .map(constraintViolation -> new ApiParameterError.Parameter(constraintViolation.getPropertyPath().toString(), constraintViolation.getMessage()))
                .collect(Collectors.toList());

        return handleError(ApiError.error(ApiErrorId.BMWFD_REQUEST_DATA_INVALID, ex.getMessage(), parameterErrors));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleException(AccessDeniedException ex) {
        return handleError(ApiError.error(HttpStatus.FORBIDDEN, ApiErrorId.BMWFD_ACCESS_DENIED, ex.getMessage()));
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<Object> handleRuntimeException(MaxUploadSizeExceededException ex) {
        return handleError(ApiError.error(HttpStatus.BAD_REQUEST, ApiErrorId.BMWFD_REQUEST_FILE_TOO_LARGE, ex.getMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleRuntimeException(RuntimeException ex) {
        LOGGER.warn("internal server error", ex);
        return handleError(ApiError.error(HttpStatus.INTERNAL_SERVER_ERROR, ApiErrorId.BMWFD_INTERNAL_SERVER_ERROR, ex.getMessage()));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<ApiParameterError.Parameter> parameterErrors = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> new ApiParameterError.Parameter(fieldError.getField(), fieldError.getDefaultMessage()))
                .collect(Collectors.toList());
        return handleError(ApiError.error(ApiErrorId.BMWFD_REQUEST_DATA_INVALID, ex.getMessage(), parameterErrors));
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if (status == HttpStatus.BAD_REQUEST) {
            return handleError(ApiError.error(ApiErrorId.BMWFD_REQUEST_DATA_INVALID, ex.getMessage(), Collections.emptyList()));
        }
        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            LOGGER.warn("internal server error", ex);
            request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
            ApiError error = ApiError.error(status, ApiErrorId.BMWFD_INTERNAL_SERVER_ERROR, ex.getMessage());
            return handleError(error);
        }
        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    private ResponseEntity<Object> handleError(ApiError error) {
        return ResponseEntity.status(error.getStatusCode()).body(error);
    }

    private enum ApiErrorId {
        BMWFD_INTERNAL_SERVER_ERROR, BMWFD_REQUEST_DATA_INVALID, BMWFD_ACCESS_DENIED, BMWFD_REQUEST_FILE_TOO_LARGE
    }
}
