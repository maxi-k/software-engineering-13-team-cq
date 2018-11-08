package de.unia.se.teamcq.calculator

import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec

class GreetingControllerTest : StringSpec() {

    private var greetingController = GreetingController()

    init {
        "test" {
            greetingController.greeting("testName").content shouldBe "Hello, testName"
        }
    }
}
