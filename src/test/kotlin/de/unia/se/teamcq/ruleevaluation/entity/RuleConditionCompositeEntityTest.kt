package de.unia.se.teamcq.ruleevaluation.entity

import de.unia.se.teamcq.TestUtils.getTestRuleConditionCompositeEntity
import de.unia.se.teamcq.TestUtils.testEqualAndHashCode
import de.unia.se.teamcq.ruleevaluation.model.LogicalConnectiveType
import io.kotlintest.specs.StringSpec
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [TestConfiguration::class])
class RuleConditionCompositeEntityTest : StringSpec() {

    init {
        "Equals and HashCode should work" {

            testEqualAndHashCode(
                    ::getTestRuleConditionCompositeEntity,
                    { it.conditionId = 1 },
                    { it.logicalConnective = LogicalConnectiveType.ANY },
                    { it.subConditions = emptyList() }
            )
        }
    }
}
