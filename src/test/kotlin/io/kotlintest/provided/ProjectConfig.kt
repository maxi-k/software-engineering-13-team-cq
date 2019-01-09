package io.kotlintest.provided

import io.kotlintest.AbstractProjectConfig
import io.kotlintest.spring.SpringAutowireConstructorExtension
import io.kotlintest.spring.SpringListener
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

object ProjectConfig : AbstractProjectConfig() {

    override fun listeners() = listOf(SpringListener)

    override fun extensions() = listOf(SpringAutowireConstructorExtension)

    private var started: Long = 0

    override fun beforeAll() {
        started = System.currentTimeMillis()
    }

    override fun afterAll() {
        val time = System.currentTimeMillis() - started
        println("overall time [ms]: " + time)
    }
}
