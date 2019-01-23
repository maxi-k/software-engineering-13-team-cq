package de.unia.se.teamcq.rulemanagement.dto

import de.unia.se.teamcq.notificationmanagement.dto.AggregatorDto
import de.unia.se.teamcq.notificationmanagement.dto.RecipientDto
import de.unia.se.teamcq.ruleevaluation.dto.RuleConditionDto
import de.unia.se.teamcq.user.dto.UserDto
import de.unia.se.teamcq.vehiclestate.dto.FleetReferenceDto
import java.io.Serializable

data class NotificationRuleDto(

    var ruleId: Long? = 0,

    var name: String?,

    var owner: UserDto?,

    var description: String?,

    var condition: RuleConditionDto?,

    var aggregator: AggregatorDto?,

    var recipients: List<RecipientDto>,

    var ownerAsAdditionalRecipient: Boolean?,

    var affectedFleets: List<FleetReferenceDto>?,

    var affectingAllApplicableFleets: Boolean?

) : Serializable {
    // Necessary for MapStruct
    constructor() : this(null, null, null, null, null, null, mutableListOf<RecipientDto>(), null,
            mutableListOf<FleetReferenceDto>(), null)
}
