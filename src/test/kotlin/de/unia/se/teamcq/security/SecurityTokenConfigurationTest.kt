package de.unia.se.teamcq.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Import
import org.springframework.test.context.ContextConfiguration
import org.springframework.util.Base64Utils
import java.security.KeyFactory
import java.security.spec.X509EncodedKeySpec
import java.util.Date

// The authentication currently receives the token from the frontend that got created by other micro services.
// However, we still need to verify that the token is valid and decrypt it using a public key to extract the username.
// Later, we will probably have a MbW service that can validate these tokens for us. Right now the MbW docker container
// doesn't contain a service like that so we need to use the public key to validate the token ourselves. For
// testing purposes, we also have a method that creates valid tokens for us using a private key.
// Both private and public key are the ones provided by MbW and can also be set in the application settings.

@ContextConfiguration(classes = [TestConfiguration::class])
@Import(JwtConfig::class, JwtTokenAuthenticationFilter::class)
class SecurityTokenConfigurationTest : StringSpec() {

    @Autowired
    lateinit var jwtConfig: JwtConfig

    @Autowired
    lateinit var jwtTokenAuthenticationFilter: JwtTokenAuthenticationFilter

    init {

        "Accept valid tokens" {

            val token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NDY1NDAzNzAsInVzZXJfbmFtZSI6ImFhYWFhYWFhLTAwMDAtYWFhYS0wMDAwLTAwMDAwMDAwMDAwMSIsImF1dGhvcml0aWVzIjpbIlJPTEVfRkRfQUxMIl0sImp0aSI6IjI0YzFkYWI2LTM1NjctNDUzYy05Mjc5LTZmYTc2ZTMzZWVlNSIsImNsaWVudF9pZCI6ImRhZDFkOWQzLTVjNTQtNGExNS1hYWEwLWFmYWYyZGY1YmM5NSIsInNjb3BlIjpbImFsbCJdfQ.CHBYsk_F4Mc5-NCTzXqT7oZM_-Vo2n5kCy90omFT04uty3vxrX-GEelIa5n5XL--6RjZP3S7QAlsBNkrntt4vflSVvbsUVg4h6nW0gs5AmkK9JQvDvZ3wK4B-xLjQ7p68P2k6SIflmf3LYC2tUq9Mi5M59YgPJ5g6icaUxv8A9OPg143y9fWCs-AEboDNF7HvZwUGc2JHPorUygitaLZBa1-pfsp7gpTWNT6dU_voxBptmuS6uOQWB4wQgzUTMJx0bLtoqcoKrRxufyA3-TXzXVoN52yEjxsDb47mVWS6ZK_kOPtBo_Uuy-9RVT0pYc1TkVSy3CzL7NSu72rCIhtAw"
            val body = decodeAndGetClaimBody(token)
            body!!["user_name"] shouldBe "aaaaaaaa-0000-aaaa-0000-000000000001"
        }

        "Create valid tokens" {

            val token = jwtTokenAuthenticationFilter.createToken("aaaaaaaa-0000-aaaa-0000-000000000001")

            val body = decodeAndGetClaimBody(token)
            body!!["user_name"] shouldBe "aaaaaaaa-0000-aaaa-0000-000000000001"
        }
    }

    private fun decodeAndGetClaimBody(token: String): Claims? {
        val signingKey = jwtConfig.publicKey

        val publicBytes = Base64Utils.decodeFromString(signingKey)

        val publicKeySpec = X509EncodedKeySpec(publicBytes)
        val keyFactory = KeyFactory.getInstance("RSA")
        val publicKey = keyFactory.generatePublic(publicKeySpec)

        // Clock from Expiration Date - 1 ms
        return Jwts.parser().setSigningKey(publicKey).setClock { Date(154654069) }.parseClaimsJws(token).body
    }
}
