package de.unia.se.teamcq.vehiclestate.model

import de.unia.se.teamcq.ruleevaluation.model.IPredicateFieldProvider
import org.springframework.stereotype.Component

@Component
class VehicleStateDataTypeFuel(

    var level: Double?,

    var liters: Int?,

    var range: Int?

) : IPredicateFieldProvider {

    // Necessary for MapStruct
    constructor() : this(null, null, null)

    override val name: String = "Fuel"
}
