package de.unia.se.teamcq.ruleevaluation.model

import java.time.Instant

enum class FieldDataType {
    TEXT {
        override fun convertToFieldType(value: String): Any = value
    },
    INTEGER {
        override fun convertToFieldType(value: String): Any = value.toInt()
    },
    LITER {
        override fun convertToFieldType(value: String): Any = value.toInt()
    },
    KILOMETER_PER_WEEK {
        override fun convertToFieldType(value: String): Any = value.toInt()
    },
    HORSEPOWER {
        override fun convertToFieldType(value: String): Any = value.toInt()
    },
    CAPACITY {
        override fun convertToFieldType(value: String): Any = value.toInt()
    },
    HOUR {
        override fun convertToFieldType(value: String): Any = value.toInt()
    },
    DAY {
        override fun convertToFieldType(value: String): Any = value.toInt()
    },
    DECIMAL {
        override fun convertToFieldType(value: String): Any = value.toDouble()
    },
    PERCENTAGE_DECIMAL {
        override fun convertToFieldType(value: String): Any = value.toDouble()
    },
    PERCENTAGE_INT {
        override fun convertToFieldType(value: String): Any = value.toDouble()
    },
    VOLTAGE {
        override fun convertToFieldType(value: String): Any = value.toDouble()
    },
    KILOMETER {
        override fun convertToFieldType(value: String): Any = value.toInt()
    },
    DATE {
        override fun convertToFieldType(value: String): Any = Instant.parse(value)
    },
    STRING_LIST {
        override fun convertToFieldType(value: String): Any = value
    },
    WEEK {
        override fun convertToFieldType(value: String): Any = value.toInt()
    };

    abstract fun convertToFieldType(value: String): Any
}
