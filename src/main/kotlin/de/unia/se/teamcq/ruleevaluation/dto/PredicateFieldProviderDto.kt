package de.unia.se.teamcq.ruleevaluation.dto

import java.io.Serializable

data class PredicateFieldProviderDto(

    var providerName: String?,

    var predicateFields: List<PredicateFieldDto>

) : Serializable {
    // Necessary for MapStruct
    constructor() : this(null, mutableListOf<PredicateFieldDto>())
}
