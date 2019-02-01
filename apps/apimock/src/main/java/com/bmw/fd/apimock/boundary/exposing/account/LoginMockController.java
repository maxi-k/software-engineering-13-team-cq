package com.bmw.fd.apimock.boundary.exposing.account;

import com.bmw.fd.apimock.boundary.exposing.error.ApiErrorId;
import com.bmw.fd.apimock.boundary.security.LoginClientCredentials;
import com.bmw.fd.spring.api.error.ApiError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


/**
 * Reuses springs oauth token endpoint to provide an access token. Additional provides a refresh token as
 * http only session cookie - this is required for single page applications as the refresh token must not be accessible
 * for java script code. The SPA may instead just call the /api/login endpoint agai. The browser will then pass the
 * refresh token as cookie and we can issue a new access token.
 */
@RestController
@RequestMapping("/api/login")
@org.springframework.web.bind.annotation.CrossOrigin
public class LoginMockController {

    private final TokenEndpoint tokenEndpoint;
    private final LoginClientCredentials loginClientCredentials;

    @Autowired
    public LoginMockController(TokenEndpoint tokenEndpoint, LoginClientCredentials loginClientCredentials) {
        this.tokenEndpoint = tokenEndpoint;
        this.loginClientCredentials = loginClientCredentials;
    }


    @PostMapping()
    public ResponseEntity login(
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "password", required = false) String password) throws HttpRequestMethodNotSupportedException {

        Map<String, String> parameters = new HashMap<>();
        var principal = new PreAuthenticatedAuthenticationToken(loginClientCredentials.getId(), loginClientCredentials.getSecret());
        principal.setAuthenticated(true);

        if (username != null && password != null) {
            parameters.put("grant_type", "password");
            parameters.put("username", username);
            parameters.put("password", password);

            ResponseEntity<OAuth2AccessToken> accessToken = tokenEndpoint.postAccessToken(principal, parameters);
            return ResponseEntity.ok(new LoginResponse(accessToken.getBody().getValue(), "firstName", "lastName"));
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping()
    public void logout() {

    }

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public ResponseEntity<ApiError> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) throws Exception {
        return toApiError(tokenEndpoint.handleHttpRequestMethodNotSupportedException(e));
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ApiError> handleException(Exception e) throws Exception {
        return toApiError(tokenEndpoint.handleException(e));
    }

    @ExceptionHandler({ClientRegistrationException.class})
    public ResponseEntity<ApiError> handleClientRegistrationException(Exception e) throws Exception {
        return toApiError(tokenEndpoint.handleClientRegistrationException(e));
    }

    @ExceptionHandler({OAuth2Exception.class})
    public ResponseEntity<ApiError> handleException(OAuth2Exception e) throws Exception {
        return toApiError(tokenEndpoint.handleException(e));
    }

    private ResponseEntity<ApiError> toApiError(ResponseEntity<OAuth2Exception> auth2ExceptionResponseEntity) {
        OAuth2Exception oAuth2Exception = auth2ExceptionResponseEntity.getBody();
        return ResponseEntity
                .status(oAuth2Exception.getHttpErrorCode())
                .body(ApiError.error(HttpStatus.valueOf(oAuth2Exception.getHttpErrorCode()), ApiErrorId.BMWFD_INVALID_LOGIN, oAuth2Exception.getMessage()));
    }

    public static class LoginResponse {

        private final String accessToken;
        private final String firstName;
        private final String lastName;

        public LoginResponse(String accessToken, String firstName, String lastName) {
            this.accessToken = accessToken;
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public String getAccessToken() {
            return accessToken;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }
    }
}
