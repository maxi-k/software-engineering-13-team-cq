package de.unia.se.teamcq.ruleevaluation.model

typealias PredicateReducer = (
    predicates: Iterable<RuleCondition>,
    predicate: (RuleCondition) -> Boolean
) -> Boolean

enum class LogicalConnectiveType {
    ANY {
        override fun getPredicateReducer(): PredicateReducer = { iterable, predicate ->
            iterable.any(predicate)
        }
    },
    ALL {
        override fun getPredicateReducer(): PredicateReducer = { iterable, predicate ->
            iterable.all(predicate)
        }
    },
    NONE {
        override fun getPredicateReducer(): PredicateReducer = { iterable, predicate ->
            iterable.none(predicate)
        }
    };
    abstract fun getPredicateReducer(): PredicateReducer
}

abstract class RuleCondition(

    var conditionId: Long? = 0

) {

    // Autogenerated
    override fun hashCode(): Int {
        return conditionId?.hashCode() ?: 0
    }

    // Autogenerated
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is RuleCondition) return false

        if (conditionId != other.conditionId) return false

        return true
    }
}
