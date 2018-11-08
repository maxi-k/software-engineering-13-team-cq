package de.unia.se.teamcq.calculator

import io.kotlintest.shouldBe
import io.kotlintest.shouldThrow
import io.kotlintest.specs.StringSpec

class EvaluationServiceTest : StringSpec() {

    var evaluationService = EvaluationService()

    init {
        "+ should work" {
            evaluationService.parseAndEvaluate("2 + 3") shouldBe 5
        }

        "* should work" {
            evaluationService.parseAndEvaluate("2 * 3") shouldBe 6
        }

        "should throw exceptions when encountering unexpected input" {
            shouldThrow<IllegalArgumentException> {
                evaluationService.parseAndEvaluate("2 / 3")
            }
        }
    }
}
