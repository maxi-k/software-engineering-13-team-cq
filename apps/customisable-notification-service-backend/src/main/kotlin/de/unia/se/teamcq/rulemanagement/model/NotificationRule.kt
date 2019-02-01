package de.unia.se.teamcq.rulemanagement.model

import de.unia.se.teamcq.notificationmanagement.model.Aggregator
import de.unia.se.teamcq.notificationmanagement.model.Recipient
import de.unia.se.teamcq.ruleevaluation.model.RuleCondition
import de.unia.se.teamcq.user.model.User
import de.unia.se.teamcq.vehiclestate.model.FleetReference
import java.io.Serializable

data class NotificationRule(

    var ruleId: Long? = 0,

    var name: String?,

    var owner: User?,

    var description: String?,

    var condition: RuleCondition?,

    var aggregator: Aggregator?,

    var recipients: List<Recipient>,

    var ownerAsAdditionalRecipient: Boolean?,

    var affectedFleets: List<FleetReference>?,

    var affectingAllApplicableFleets: Boolean?

) : Serializable {
    constructor() : this(null, null, null, null, null, null, mutableListOf<Recipient>(), null,
            mutableListOf<FleetReference>(), null)
}
