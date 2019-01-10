package de.unia.se.teamcq.rulemanagement.model

import de.unia.se.teamcq.notificationmanagement.Aggregator
import de.unia.se.teamcq.notificationmanagement.ImmediateAggregator
import de.unia.se.teamcq.user.User
import de.unia.se.teamcq.user.UserNotificationType
import de.unia.se.teamcq.user.UserSettings

class NotificationRuleBuilder {

    var name: String = ""
        private set
    var recipients: MutableList<String> = mutableListOf()
        private set
    var description: String = ""
        private set
    // var fleets: MutableList<Fleet> = mutableListOf()
        // private set
    // var formula: Formula = Formula ()
        // private set
    var aggregator: Aggregator = ImmediateAggregator()
        private set

    var user: User = User(null, null, UserSettings(UserNotificationType.EMAIL))
        private set

    fun withName(name: String): NotificationRuleBuilder = apply { this.name = name }

    fun withRecipients(recipients: MutableList<String>): NotificationRuleBuilder = apply { this.recipients = recipients }

    fun withDescription(description: String): NotificationRuleBuilder = apply { this.description = description }

    // fun withFleets(fleets: MutableList<Fleet>): NotificationRuleBuilder = apply { this.fleets = fleets }

    // fun withFormula(formula: Formula): NotificationRuleBuilder = apply { this.formula = formula }

    fun withAggregator(aggregator: Aggregator): NotificationRuleBuilder = apply { this.aggregator = aggregator }

    fun withUser(user: User): NotificationRuleBuilder = apply { this.user = user }

    fun build(): NotificationRule = NotificationRule(0, name, description) // , fleets, formula, aggregator, user)
}
