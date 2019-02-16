package de.unia.se.teamcq.ruleevaluation.model

import java.time.Instant

enum class FieldDataType {
    TEXT {
        override fun convertToFieldType(value: String): Any = value
    },
    INTEGER {
        override fun convertToFieldType(value: String): Any = value.toInt()
    },
    DECIMAL {
        override fun convertToFieldType(value: String): Any = value.toDouble()
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
