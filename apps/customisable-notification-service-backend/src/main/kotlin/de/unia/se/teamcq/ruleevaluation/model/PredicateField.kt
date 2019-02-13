package de.unia.se.teamcq.ruleevaluation.model

import java.lang.IllegalArgumentException
import java.text.DateFormat

enum class FieldDataType {
    TEXT {
        override fun convertToFieldType(value: String): Any = value
    },
    INTEGER {
        override fun convertToFieldType(value: String): Any = value.toInt()
    },
    DECIMAL {
        override fun convertToFieldType(value: String): Any = value.toFloat()
    },
    DATE {
        override fun convertToFieldType(value: String): Any = DateFormat.getDateInstance().parse(value)
    },
    STRING_LIST {
        override fun convertToFieldType(value: String): Any = value
    },
    WEEK {
        override fun convertToFieldType(value: String): Any = value.toInt()
    };

    abstract fun convertToFieldType(value: String): Any;
}

enum class ComparisonType {
    EQUAL_TO {
        override fun <T> compare(firstValue: Comparable<T>, secondValue: T): Boolean =
                firstValue == secondValue
    },
    NOT_EQUAL_TO {
        override fun <T> compare(firstValue: Comparable<T>, secondValue: T): Boolean =
                firstValue != secondValue
    },
    GREATER_THAN {
        override fun <T> compare(firstValue: Comparable<T>, secondValue: T): Boolean =
                firstValue > secondValue
    },
    LESSER_THAN {
        override fun <T> compare(firstValue: Comparable<T>, secondValue: T): Boolean =
                firstValue < secondValue
    },
    GREATER_THAN_OR_EQUAL_TO {

        override fun <T> compare(firstValue: Comparable<T>, secondValue: T): Boolean =
                firstValue >= secondValue
    },
    LESSER_THAN_OR_EQUAL_TO {
        override fun <T> compare(firstValue: Comparable<T>, secondValue: T): Boolean =
                firstValue <= secondValue
    },
    CONTAINED_IN {
        override fun <T> compare(firstValue: Iterable<T>, secondValue: T): Boolean =
                firstValue.contains(secondValue)
    },
    NOT_CONTAINED_IN {
        override fun <T> compare(firstValue: Iterable<T>, secondValue: T): Boolean =
                firstValue.contains(secondValue)
    };

    open fun <T> compare(firstValue: Comparable<T>, secondValue: T): Boolean {
        throw IllegalArgumentException()
    }

    open fun <T> compare(firstValue: Iterable<T>, secondValue: T): Boolean {
        throw IllegalArgumentException()
    }
}

object EvaluationStrategies {
    val NUMERIC = listOf(ComparisonType.EQUAL_TO,
        ComparisonType.NOT_EQUAL_TO,
        ComparisonType.GREATER_THAN,
        ComparisonType.LESSER_THAN,
        ComparisonType.GREATER_THAN_OR_EQUAL_TO,
        ComparisonType.LESSER_THAN_OR_EQUAL_TO
    )

    val LIST = listOf(ComparisonType.EQUAL_TO,
        ComparisonType.NOT_EQUAL_TO,
        ComparisonType.CONTAINED_IN,
        ComparisonType.NOT_CONTAINED_IN
    )

    val TEXT = listOf(ComparisonType.EQUAL_TO,
        ComparisonType.NOT_EQUAL_TO
    )
}

data class PredicateField<ContainerType, FieldType>(

    var fieldName: String?,

    var dataType: FieldDataType?,

    var possibleEvaluationStrategies: List<ComparisonType>,

    var fieldValueAccessor: (ContainerType) -> FieldType?

) {
    // Necessary for MapStruct
    constructor() : this(null, null, mutableListOf<ComparisonType>(), { null })
}
