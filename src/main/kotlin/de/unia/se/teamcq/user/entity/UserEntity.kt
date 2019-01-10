package de.unia.se.teamcq.user.entity

import de.unia.se.teamcq.rulemanagement.entity.NotificationRuleEntity
import java.io.Serializable
import javax.persistence.Id
import javax.persistence.Entity
import javax.persistence.OneToMany

@Entity
data class UserEntity(

    @Id
    var name: String?,

    var mailAddress: String? = null,

    var cellPhoneNumber: String? = null,

    var userSettings: UserSettingsEntity?,

    @OneToMany(mappedBy = "owner")
    var notificationRules: Set<NotificationRuleEntity>?

) : Serializable {
    constructor() : this(null, null, null, null, null)

    fun addNotificationRuleEntity(notificationRuleEntity: NotificationRuleEntity): UserEntity {
        this.notificationRules = this.notificationRules.orEmpty().plus(notificationRuleEntity)
        notificationRuleEntity.owner = this
        return this
    }
}
