package de.unia.se.teamcq.security

import io.jsonwebtoken.Clock
import io.jsonwebtoken.Jwts
import io.kotlintest.specs.StringSpec
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.test.context.ContextConfiguration
import org.springframework.util.Base64Utils
import java.security.Key
import java.security.KeyFactory
import java.security.PublicKey
import java.security.spec.X509EncodedKeySpec
import java.util.*
import kotlin.test.assertEquals

@ContextConfiguration(classes = [(TestConfiguration::class)])
class SecurityTokenConfigTest : StringSpec() {

    init {

        "Accept valid tokens" {

            val token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NDY1NDAzNzAsInVzZXJfbmFtZSI6ImFhYWFhYWFhLTAwMDAtYWFhYS0wMDAwLTAwMDAwMDAwMDAwMSIsImF1dGhvcml0aWVzIjpbIlJPTEVfRkRfQUxMIl0sImp0aSI6IjI0YzFkYWI2LTM1NjctNDUzYy05Mjc5LTZmYTc2ZTMzZWVlNSIsImNsaWVudF9pZCI6ImRhZDFkOWQzLTVjNTQtNGExNS1hYWEwLWFmYWYyZGY1YmM5NSIsInNjb3BlIjpbImFsbCJdfQ.CHBYsk_F4Mc5-NCTzXqT7oZM_-Vo2n5kCy90omFT04uty3vxrX-GEelIa5n5XL--6RjZP3S7QAlsBNkrntt4vflSVvbsUVg4h6nW0gs5AmkK9JQvDvZ3wK4B-xLjQ7p68P2k6SIflmf3LYC2tUq9Mi5M59YgPJ5g6icaUxv8A9OPg143y9fWCs-AEboDNF7HvZwUGc2JHPorUygitaLZBa1-pfsp7gpTWNT6dU_voxBptmuS6uOQWB4wQgzUTMJx0bLtoqcoKrRxufyA3-TXzXVoN52yEjxsDb47mVWS6ZK_kOPtBo_Uuy-9RVT0pYc1TkVSy3CzL7NSu72rCIhtAw"
            val signingKey =
                    "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAyFa0WF36CXzu6xU7tNk5" +
                    "X5vwgERlUuWqt8Krut3lgjDEnc3MOB4AyU8ucun+z01avGF3xDN7eQpoGdmJScNA" +
                    "c1tmGsTht5ozzTz1Xoa0sg9nlA3nVbN5BItV++1vdJgENwY6Lrwl7kvTqlAjGb8C" +
                    "0lS8dgtctFABbV+bDEoRIfCdlek2lR/Z4JHWtojdGrjks0lTACV2cT54aWc5TyYp" +
                    "8Xa7ToLKDRvnQQnUmAQyOjPJ2YOZL8r42Du6td6k5gKzF7QRpWfQN8ycvBdf56mp" +
                    "3xUl1fMq2RrodBltWeC/Vs6hqv3bh8WZyu07wK8sEc1bT1lCCwdFHRKjPr5kdvFA" +
                    "2QIDAQAB"

            val publicBytes = Base64Utils.decodeFromString(signingKey)

            val bobPubKeySpec = X509EncodedKeySpec(publicBytes)
            val keyFactory = KeyFactory.getInstance("RSA")
            val bobPubKey = keyFactory.generatePublic(bobPubKeySpec)


            // Clock from Expiration Date - 1 ms
            val body = Jwts.parser().setSigningKey(bobPubKey).setClock { Date(154654069) }.parseClaimsJws(token).body
            assertEquals(body["user_name"], "aaaaaaaa-0000-aaaa-0000-000000000001")
        }
    }
}
