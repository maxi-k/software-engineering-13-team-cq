package de.unia.se.teamcq.ruleevaluation.dto

import java.io.Serializable

// Constructor with (null)-default values for everything necessary for MapStruct
data class PredicateFieldProviderDto(

    var providerName: String? = null,

    var predicateFields: List<PredicateFieldDto> = mutableListOf()

) : Serializable
