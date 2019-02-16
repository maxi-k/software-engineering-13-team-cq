package de.unia.se.teamcq.rulemanagement.dto

import de.unia.se.teamcq.notificationmanagement.dto.AggregatorDto
import de.unia.se.teamcq.notificationmanagement.dto.RecipientDto
import de.unia.se.teamcq.ruleevaluation.dto.RuleConditionDto
import de.unia.se.teamcq.user.dto.UserDto
import de.unia.se.teamcq.vehiclestate.dto.FleetReferenceDto
import java.io.Serializable

// Constructor with (null)-default values for everything necessary for MapStruct
data class NotificationRuleDto(

    var ruleId: Long? = null,

    var name: String? = null,

    var owner: UserDto? = null,

    var description: String? = null,

    var condition: RuleConditionDto? = null,

    var aggregator: AggregatorDto? = null,

    var recipients: List<RecipientDto> = mutableListOf(),

    var ownerAsAdditionalRecipient: Boolean? = null,

    var affectedFleets: List<FleetReferenceDto> = mutableListOf(),

    var affectingAllApplicableFleets: Boolean? = null

) : Serializable
