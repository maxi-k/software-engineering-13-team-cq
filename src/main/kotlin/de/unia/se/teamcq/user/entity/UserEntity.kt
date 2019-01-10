package de.unia.se.teamcq.user.entity

import de.unia.se.teamcq.rulemanagement.entity.NotificationRuleEntity
import java.io.Serializable
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.OneToOne
import javax.persistence.OneToMany
import javax.persistence.CascadeType
import javax.persistence.FetchType

@Entity
class UserEntity(

    @Id
    var name: String?,

    var mailAddress: String? = null,

    var cellPhoneNumber: String? = null,

    @OneToOne(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    var userSettings: UserSettingsEntity?,

    // Eager should to be changed to lazy if the amount of entities we were told to expect
    // per user is actually bigger
    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
    var notificationRules: Set<NotificationRuleEntity>?

) : Serializable {
    constructor() : this(null, null, null, null, null)

    fun addNotificationRuleEntity(notificationRuleEntity: NotificationRuleEntity): UserEntity {
        this.notificationRules = this.notificationRules.orEmpty().plus(notificationRuleEntity)
        notificationRuleEntity.owner = this
        return this
    }
}
