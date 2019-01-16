package de.unia.se.teamcq.vehiclestate.model

import de.unia.se.teamcq.ruleevaluation.model.IPredicateFieldProvider
import org.springframework.stereotype.Component

@Component
data class VehicleStateDataTypeBattery(

    var charge: Double?,

    var voltage: Double?,

    var status: String?

) : IPredicateFieldProvider {

    // Necessary for MapStruct
    constructor() : this(null, null, null)

    override val name: String = "Battery"
}
