package de.unia.se.teamcq.security.service

import org.springframework.stereotype.Service
import org.springframework.web.client.RestClientException

/**
 * The service that handles fetching API-Tokens.
 */
@Service
interface IAuthenticationTokenService {

    /**
     * Fetch a API-Token
     *
     * Fetches a new API-Token from the Authentication Service
     *
     * @return All header information needed to communicate with other services
     */
    @Throws(RestClientException::class, NullPointerException::class)
    fun getAuthenticationHeader(): Pair<String, String>
}
