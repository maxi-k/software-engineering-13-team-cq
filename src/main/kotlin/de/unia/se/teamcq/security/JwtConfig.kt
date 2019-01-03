package de.unia.se.teamcq.security

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
data class JwtConfig(
    @Value("\${security.jwt.uri:/auth/**}")
    val Uri: String,

    @Value("\${security.jwt.header:Authorization}")
    val header: String,

    @Value("\${security.jwt.prefix:Bearer }")
    val prefix: String,

    @Value("\${security.jwt.expiration:#{24*60*60}}")
    val expiration: Int,

    @Value("\${security.jwt.secret:yPWeYz4xsniPLDGNJtVPhs8o7DbR3svbeJUFqHWn8LpRXRGKM9sh4mDGBooEnNCE}")
    val secret: String
)
