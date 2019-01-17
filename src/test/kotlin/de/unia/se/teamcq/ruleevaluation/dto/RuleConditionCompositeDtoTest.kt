package de.unia.se.teamcq.ruleevaluation.dto

import de.unia.se.teamcq.TestUtils.getTestRuleConditionCompositeDto
import de.unia.se.teamcq.TestUtils.testEqualAndHashCode
import de.unia.se.teamcq.ruleevaluation.model.LogicalConnectiveType
import io.kotlintest.specs.StringSpec
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [TestConfiguration::class])
class RuleConditionCompositeDtoTest : StringSpec() {

    init {
        "Equal and HashCode should work" {

            testEqualAndHashCode(
                    ::getTestRuleConditionCompositeDto,
                    { it.conditionId = 1 },
                    { it.logicalConnective = LogicalConnectiveType.ANY },
                    { it.subConditions = emptyList() }
            )
        }


    }
}
