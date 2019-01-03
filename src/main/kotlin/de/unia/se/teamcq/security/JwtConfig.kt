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

    @Value("\${security.jwt.secret:" + "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAyFa0WF36CXzu6xU7tNk5" +
            "X5vwgERlUuWqt8Krut3lgjDEnc3MOB4AyU8ucun+z01avGF3xDN7eQpoGdmJScNA" +
            "c1tmGsTht5ozzTz1Xoa0sg9nlA3nVbN5BItV++1vdJgENwY6Lrwl7kvTqlAjGb8C" +
            "0lS8dgtctFABbV+bDEoRIfCdlek2lR/Z4JHWtojdGrjks0lTACV2cT54aWc5TyYp" +
            "8Xa7ToLKDRvnQQnUmAQyOjPJ2YOZL8r42Du6td6k5gKzF7QRpWfQN8ycvBdf56mp" +
            "3xUl1fMq2RrodBltWeC/Vs6hqv3bh8WZyu07wK8sEc1bT1lCCwdFHRKjPr5kdvFA" +
            "2QIDAQAB" + "}")
    val publicKey: String
)
