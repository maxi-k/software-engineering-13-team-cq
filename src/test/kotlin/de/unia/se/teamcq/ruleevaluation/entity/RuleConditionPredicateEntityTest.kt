package de.unia.se.teamcq.ruleevaluation.entity

import de.unia.se.teamcq.TestUtils.getTestRuleConditionPredicateEntity
import de.unia.se.teamcq.TestUtils.testEqualAndHashCode
import de.unia.se.teamcq.ruleevaluation.model.ComparisonType
import io.kotlintest.specs.StringSpec
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [TestConfiguration::class])
class RuleConditionPredicateEntityTest : StringSpec() {

    init {
        "Equals and HashCode should work" {

            testEqualAndHashCode(
                    ::getTestRuleConditionPredicateEntity,
                    { it.conditionId = 1 },
                    { it.comparisonType = ComparisonType.NOT_EQUAL_TO },
                    { it.comparisonValue = "test" },
                    { it.fieldName = "test" },
                    { it.providerName = "test" }
            )
        }
    }
}
