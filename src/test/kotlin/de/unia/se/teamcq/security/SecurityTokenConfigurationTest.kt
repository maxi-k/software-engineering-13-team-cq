package de.unia.se.teamcq.security

import de.unia.se.teamcq.user.service.IUserService
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.kotlintest.should
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.OverrideMockKs
import io.mockk.mockkClass
import io.mockk.mockkStatic
import io.mockk.verify
import io.mockk.clearMocks
import io.mockk.unmockkAll
import io.mockk.just
import io.mockk.Runs
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.test.context.ContextConfiguration
import org.springframework.util.Base64Utils
import java.security.KeyFactory
import java.security.spec.X509EncodedKeySpec
import java.util.Date
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

// The authentication currently receives the token from the frontend that got created by other micro services.
// However, we still need to verify that the token is valid and decrypt it using a public key to extract the username.
// Later, we will probably have a MbW service that can validate these tokens for us. Right now the MbW docker container
// doesn't contain a service like that so we need to use the public key to validate the token ourselves. For
// testing purposes, we also have a method that creates valid tokens for us using a private key.
// Both private and public key are the ones provided by MbW and can also be set in the application settings.

@ContextConfiguration(classes = [(TestConfiguration::class)])
class SecurityTokenConfigurationTest : StringSpec() {

    @MockK
    lateinit var jwtConfig: JwtConfig

    @MockK
    lateinit var userService: IUserService

    @OverrideMockKs
    lateinit var jwtTokenAuthenticationFilter: JwtTokenAuthenticationFilter

    init {
        MockKAnnotations.init(this)

        every { userService.getOrCreateUser(any()) } returns null

        every { jwtConfig.publicKey } returns JwtTestConfig.publicKey
        every { jwtConfig.privateKey } returns JwtTestConfig.privateKey
        every { jwtConfig.header } returns JwtTestConfig.header
        every { jwtConfig.prefix } returns JwtTestConfig.prefix
        every { jwtConfig.expiration } returns JwtTestConfig.expiration

        "Tokens that used to work should continue to work using our mechanism if expiration is ignored" {

            val token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NDY1NDAzNzAsInVzZXJfbmFtZSI6ImFhYWFhYWFhLTAwMDAtYWFhYS0wMDAwLTAwMDAwMDAwMDAwMSIsImF1dGhvcml0aWVzIjpbIlJPTEVfRkRfQUxMIl0sImp0aSI6IjI0YzFkYWI2LTM1NjctNDUzYy05Mjc5LTZmYTc2ZTMzZWVlNSIsImNsaWVudF9pZCI6ImRhZDFkOWQzLTVjNTQtNGExNS1hYWEwLWFmYWYyZGY1YmM5NSIsInNjb3BlIjpbImFsbCJdfQ.CHBYsk_F4Mc5-NCTzXqT7oZM_-Vo2n5kCy90omFT04uty3vxrX-GEelIa5n5XL--6RjZP3S7QAlsBNkrntt4vflSVvbsUVg4h6nW0gs5AmkK9JQvDvZ3wK4B-xLjQ7p68P2k6SIflmf3LYC2tUq9Mi5M59YgPJ5g6icaUxv8A9OPg143y9fWCs-AEboDNF7HvZwUGc2JHPorUygitaLZBa1-pfsp7gpTWNT6dU_voxBptmuS6uOQWB4wQgzUTMJx0bLtoqcoKrRxufyA3-TXzXVoN52yEjxsDb47mVWS6ZK_kOPtBo_Uuy-9RVT0pYc1TkVSy3CzL7NSu72rCIhtAw"
            val body = decodeAndGetClaimBody(token)
            body!!["user_name"] shouldBe "aaaaaaaa-0000-aaaa-0000-000000000001"
        }

        "CreateToken should create valid tokens" {

            val token = jwtTokenAuthenticationFilter.createToken("aaaaaaaa-0000-aaaa-0000-000000000001")

            val body = decodeAndGetClaimBody(token)
            body!!["user_name"] shouldBe "aaaaaaaa-0000-aaaa-0000-000000000001"
        }

        "DoFilterInternal" should {

            "Not accept invalid tokens" {

                mockkStatic("org.springframework.security.core.context.SecurityContextHolder")

                val mockedSecurityContext = mockkClass(SecurityContext::class)
                every { SecurityContextHolder.getContext() } returns mockedSecurityContext

                val httpServletRequest = mockkClass(HttpServletRequest::class)
                val httpServletResponse = mockkClass(HttpServletResponse::class)
                val filterChain = mockkClass(FilterChain::class)

                every { filterChain.doFilter(any(), any()) } just Runs
                val header = JwtTestConfig.prefix + "faketoken"
                every { httpServletRequest.getHeader("Authorization") } returns header

                invokeDoFilterInternal(jwtTokenAuthenticationFilter, httpServletRequest, httpServletResponse, filterChain)

                verify(exactly = 1) {
                    SecurityContextHolder.clearContext()
                }
                unmockkAll() // To clear the static mock
            }

            "Set the username in the securityContext and accept correct tokens" {

                mockkStatic("org.springframework.security.core.context.SecurityContextHolder")

                val mockedSecurityContext = mockkClass(SecurityContext::class)
                every { SecurityContextHolder.getContext() } returns mockedSecurityContext

                val httpServletRequest = mockkClass(HttpServletRequest::class)
                val httpServletResponse = mockkClass(HttpServletResponse::class)
                val filterChain = mockkClass(FilterChain::class)

                every { filterChain.doFilter(any(), any()) } just Runs
                val header = JwtTestConfig.prefix + jwtTokenAuthenticationFilter.createToken("aaaaaaaa-0000-aaaa-0000-000000000001")
                every { httpServletRequest.getHeader("Authorization") } returns header

                invokeDoFilterInternal(jwtTokenAuthenticationFilter, httpServletRequest, httpServletResponse, filterChain)

                val expectedAuthentication = UsernamePasswordAuthenticationToken(
                        "aaaaaaaa-0000-aaaa-0000-000000000001", null, emptyList())

                verify(exactly = 1) {
                    mockedSecurityContext.authentication = expectedAuthentication
                }
                unmockkAll() // To clear the static mock
            }

            "Create user in DB if he doesn't exist already" {

                clearMocks(userService) // To reset the verify count

                val httpServletRequest = mockkClass(HttpServletRequest::class)
                val httpServletResponse = mockkClass(HttpServletResponse::class)
                val filterChain = mockkClass(FilterChain::class)

                every { filterChain.doFilter(any(), any()) } just Runs
                val header = JwtTestConfig.prefix + jwtTokenAuthenticationFilter.createToken("aaaaaaaa-0000-aaaa-0000-000000000001")
                every { httpServletRequest.getHeader("Authorization") } returns header

                invokeDoFilterInternal(jwtTokenAuthenticationFilter, httpServletRequest, httpServletResponse, filterChain)

                verify(exactly = 1) {
                    userService.getOrCreateUser("aaaaaaaa-0000-aaaa-0000-000000000001")
                }
                unmockkAll() // To clear the static mock
            }
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

    // Can't test this without reflection because Kotlin interprets protected differently than Java and
    // JwtTokenAuthenticationFilter overrides a protected Spring method
    private fun invokeDoFilterInternal(
        jwtTokenAuthenticationFilter: JwtTokenAuthenticationFilter,
        httpServletRequest: HttpServletRequest,
        httpServletResponse: HttpServletResponse,
        filterChain: FilterChain
    ) {

        val method = jwtTokenAuthenticationFilter.javaClass.getDeclaredMethod("doFilterInternal", HttpServletRequest::class.java, HttpServletResponse::class.java, FilterChain::class.java)
        method.isAccessible = true
        method.invoke(jwtTokenAuthenticationFilter, httpServletRequest, httpServletResponse, filterChain)
    }
}
