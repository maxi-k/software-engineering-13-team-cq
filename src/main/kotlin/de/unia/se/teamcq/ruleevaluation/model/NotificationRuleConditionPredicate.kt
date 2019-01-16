package de.unia.se.teamcq.ruleevaluation.model

data class NotificationRuleConditionPredicate(

    var predicateId: Long? = 0,

    var providerName: String?,

    var comparisonType: ComparisonType?,

    var comparisonValue: String?

) {
    // Necessary for MapStruct
    constructor() : this(null, null, null, null)
}
