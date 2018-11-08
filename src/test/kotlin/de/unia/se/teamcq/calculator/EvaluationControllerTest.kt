package de.unia.se.teamcq.calculator

import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.every

class EvaluationControllerTest : StringSpec() {

    @MockK
    private lateinit var evaluationService: EvaluationService

    @InjectMockKs
    private lateinit var evaluationController: EvaluationController

    init {
        MockKAnnotations.init(this)

        "test" {
            every { evaluationService.parseAndEvaluate("3 + 2") } returns 5

            evaluationController.eval("3 + 2").value shouldBe "5"
        }
    }
}
