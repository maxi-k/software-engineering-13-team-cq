package de.unia.se.teamcq.security.service

import de.bmw.authentication.api.LoginApi
import de.unia.se.teamcq.security.JwtConfig
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClientException

@Component
class AuthenticationTokenAdapter : IAuthenticationTokenAdapter {

    @Value("\${de.unia.se.teamcq.bmw-authentication.username:name}")
    private lateinit var authenticationUsername: String

    @Value("\${de.unia.se.teamcq.bmw-authentication.password:pw}")
    private lateinit var authenticationPassword: String

    @Autowired
    private lateinit var jwtConfig: JwtConfig

    @Throws(RestClientException::class, NullPointerException::class)
    override fun getAuthenticationHeader(): Pair<String, String> {
        val loginApi = LoginApi()
        val loginResult = loginApi.login(authenticationUsername, authenticationPassword)
        logger.info("Login successful", loginResult.toString())
        return Pair(jwtConfig.header, "${jwtConfig.prefix}${loginResult!!.accessToken}")
    }

    companion object {
        private val logger = LoggerFactory.getLogger(AuthenticationTokenAdapter::class.java)
    }
}
