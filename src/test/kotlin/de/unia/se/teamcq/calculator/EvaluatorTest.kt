package de.unia.se.teamcq.calculator

import io.kotlintest.shouldBe
import io.kotlintest.shouldThrow
import io.kotlintest.specs.StringSpec

class EvaluatorTest : StringSpec() {
    init {
        "+ should work" {
            Evaluator.parseAndEval("2 + 3") shouldBe 5
        }

        "* should work" {
            Evaluator.parseAndEval("2 * 3") shouldBe 6
        }

        "should throw exceptions when encountering unexpected input" {
            shouldThrow<IllegalArgumentException> {
                Evaluator.parseAndEval("2 / 3")
            }
        }
    }
}
