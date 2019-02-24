package de.unia.se.teamcq.rulemanagement.model

import de.unia.se.teamcq.notificationmanagement.model.Aggregator
import de.unia.se.teamcq.notificationmanagement.model.Recipient
import de.unia.se.teamcq.ruleevaluation.model.RuleCondition
import de.unia.se.teamcq.user.model.User
import de.unia.se.teamcq.vehiclestate.model.FleetReference
import java.sql.Timestamp

// Constructor with (null)-default values for everything necessary for MapStruct
data class NotificationRule(

    var ruleId: Long? = null,

    var name: String? = null,

    var owner: User? = null,

    var description: String? = null,

    var condition: RuleCondition? = null,

    var aggregator: Aggregator? = null,

    var recipients: List<Recipient> = mutableListOf(),

    var ownerAsAdditionalRecipient: Boolean? = null,

    var affectedFleets: List<FleetReference> = mutableListOf(),

    var affectingAllApplicableFleets: Boolean? = null,

    var lastUpdate: Timestamp? = null

)
