package de.unia.se.teamcq.vehiclestate.model

import de.unia.se.teamcq.ruleevaluation.model.IPredicateFieldProvider
import org.springframework.stereotype.Component

@Component
class VehicleStateDataTypeMileage(

    var current: Int?,

    var remaining: Int?,

    var reached: Int?

) : IPredicateFieldProvider {

    // Necessary for MapStruct
    constructor() : this(null, null, null)

    override val name: String = "Mileage"
}
