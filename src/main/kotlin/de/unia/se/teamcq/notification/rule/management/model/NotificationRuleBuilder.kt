package de.unia.se.teamcq.notification.rule.management.model

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
    // var aggregator: Aggregator = Aggregator ()
        // private set
    // var user: User = User ()
        // private set

    fun withName(name: String): NotificationRuleBuilder = apply { this.name = name }

    fun withRecipients(recipients: MutableList<String>): NotificationRuleBuilder = apply { this.recipients = recipients }

    fun withDescription(description: String): NotificationRuleBuilder = apply { this.description = description }

    // fun withFleets(fleets: MutableList<Fleet>): NotificationRuleBuilder = apply { this.fleets = fleets }

    // fun withFormula(formula: Formula): NotificationRuleBuilder = apply { this.formula = formula }

    // fun withAggregator(aggregator: Aggregator): NotificationRuleBuilder = apply { this.aggregator = aggregator }

    // fun withUser(user: User): NotificationRuleBuilder = apply { this.user = user }

    fun build(): NotificationRule = NotificationRule(0, name, recipients, description) // , fleets, formula, aggregator, user)
}
