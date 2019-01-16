package de.unia.se.teamcq.ruleevaluation.dto

import java.io.Serializable

data class PredicateFieldProviderDto(

    var name: String?

) : Serializable {
    // Necessary for MapStruct
    constructor() : this(null)
}
