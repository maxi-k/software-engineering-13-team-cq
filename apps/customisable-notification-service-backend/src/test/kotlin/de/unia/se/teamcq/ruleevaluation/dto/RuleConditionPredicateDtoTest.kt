package de.unia.se.teamcq.ruleevaluation.dto

import de.unia.se.teamcq.TestUtils.getTestRuleConditionPredicateDto
import de.unia.se.teamcq.TestUtils.testEqualAndHashCode
import de.unia.se.teamcq.ruleevaluation.model.ComparisonType
import io.kotlintest.specs.StringSpec
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [TestConfiguration::class])
class RuleConditionPredicateDtoTest : StringSpec() {

    init {
        "Equals and HashCode should work" {

            testEqualAndHashCode(
                    ::getTestRuleConditionPredicateDto,
                    { it.conditionId = 1 },
                    { it.comparisonType = ComparisonType.NOT_EQUAL_TO },
                    { it.comparisonValue = "test" },
                    { it.fieldName = "test" },
                    { it.providerName = "test" }
            )
        }
    }
}
