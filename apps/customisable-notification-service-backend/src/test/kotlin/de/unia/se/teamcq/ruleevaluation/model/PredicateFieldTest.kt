package de.unia.se.teamcq.ruleevaluation.model

import io.kotlintest.data.forall
import io.kotlintest.shouldBe
import io.kotlintest.shouldThrow
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.test.context.ContextConfiguration
import java.lang.IllegalArgumentException
import java.time.LocalTime

@ContextConfiguration(classes = [TestConfiguration::class])
class PredicateFieldTest : StringSpec() {

    init {
        "FieldDataType Conversion transforms String correctly" {
            val testedDate = LocalTime.now()
            forall(
                row(FieldDataType.TEXT, "Hello World", "Hello World"),
                row(FieldDataType.WEEK, "42", 42),
                row(FieldDataType.DECIMAL, "10.2", 10.2f),
                row(FieldDataType.DATE, testedDate.toString(), testedDate),
                row(FieldDataType.STRING_LIST, "string as part of list", "string as part of list")
            ) { fieldDataType, inputValue, outputValue ->
                fieldDataType.convertToFieldType(inputValue) shouldBe outputValue
            }
        }

        "ComparisonType EQUAL_TO should compare values correctly" {
            ComparisonType.EQUAL_TO.compare(10.5f, 10.5f) shouldBe true
            ComparisonType.EQUAL_TO.compare(10.5f, 10.50001f) shouldBe false
            shouldThrow<IllegalArgumentException> {
                ComparisonType.EQUAL_TO.compare(listOf(31.4f), 31.4f)
            }
        }

        "ComparisonType NOT_EQUAL_TO should compare values correctly" {
            ComparisonType.NOT_EQUAL_TO.compare("test1", "Test1") shouldBe true
            ComparisonType.NOT_EQUAL_TO.compare(3.14158, 3.14158) shouldBe false
            shouldThrow<IllegalArgumentException> {
                ComparisonType.NOT_EQUAL_TO.compare(listOf(31.4f), 31.4f)
            }
        }

        "ComparisonType CONTAINED_IN should compare values correctly" {
            ComparisonType.CONTAINED_IN.compare(listOf("vin1", "vin2"), "vin1") shouldBe true
            ComparisonType.CONTAINED_IN.compare(listOf("vin1", "vin2"), "vin3") shouldBe false
            shouldThrow<IllegalArgumentException> {
                ComparisonType.CONTAINED_IN.compare(31.4f, 31.4f)
            }
        }

        "ComparisonType NOT_CONTAINED_IN should compare values correctly" {
            ComparisonType.NOT_CONTAINED_IN.compare(listOf("vin1", "vin2"), "vin1") shouldBe false
            ComparisonType.NOT_CONTAINED_IN.compare(listOf("vin1", "vin2"), "vin3") shouldBe true
            shouldThrow<IllegalArgumentException> {
                ComparisonType.NOT_CONTAINED_IN.compare(31.4f, 31.4f)
            }
        }
    }
}
