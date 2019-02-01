package de.unia.se.teamcq.ruleevaluation.model

import de.unia.se.teamcq.TestUtils.getTestRuleConditionPredicateModel
import de.unia.se.teamcq.TestUtils.testEqualAndHashCode
import io.kotlintest.specs.StringSpec
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [TestConfiguration::class])
class RuleConditionPredicateTest : StringSpec() {

    init {
        "Equals and HashCode should work" {

            testEqualAndHashCode(
                    ::getTestRuleConditionPredicateModel,
                    { it.conditionId = 1 },
                    { it.comparisonType = ComparisonType.NOT_EQUAL_TO },
                    { it.comparisonValue = "test" },
                    { it.fieldName = "test" },
                    { it.providerName = "test" }
            )
        }
    }
}
