package de.unia.se.teamcq.ruleevaluation.model

class RuleConditionPredicate(

    conditionId: Long? = 0,

    var providerName: String?,

    var fieldName: String?,

    var comparisonType: ComparisonType?,

    var comparisonValue: String?

) : RuleCondition(conditionId){
    // Necessary for MapStruct
    constructor() : this(null, null, null, null, null)
}
