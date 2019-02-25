package de.unia.se.teamcq.user.entity

import de.unia.se.teamcq.rulemanagement.entity.NotificationRuleEntity
import java.io.Serializable
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.OneToOne

@Entity
// Constructor with (null)-default values for everything necessary for MapStruct
class UserEntity(

    @Id
    var name: String? = null,

    var mailAddress: String? = null,

    var cellPhoneNumber: String? = null,

    @OneToOne(cascade = [CascadeType.ALL], fetch = FetchType.EAGER, optional = false)
    var userSettings: UserSettingsEntity? = null,

    // Eager should to be changed to lazy if the amount of entities we were told to expect
    // per user is actually bigger
    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
    var notificationRules: Set<NotificationRuleEntity> = mutableSetOf()

) : Serializable {

    fun addNotificationRuleEntity(notificationRuleEntity: NotificationRuleEntity): UserEntity {
        this.notificationRules = this.notificationRules.plus(notificationRuleEntity)
        notificationRuleEntity.owner = this
        return this
    }

    // Autogenerated. Removed notificationRules to prevent cyclic references
    override fun hashCode(): Int {
        var result = name?.hashCode() ?: 0
        result = 31 * result + (mailAddress?.hashCode() ?: 0)
        result = 31 * result + (cellPhoneNumber?.hashCode() ?: 0)
        result = 31 * result + (userSettings?.hashCode() ?: 0)
        return result
    }

    // Autogenerated. Removed notificationRules to prevent cyclic references
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is UserEntity) return false

        if (name != other.name) return false
        if (mailAddress != other.mailAddress) return false
        if (cellPhoneNumber != other.cellPhoneNumber) return false
        if (userSettings != other.userSettings) return false

        return true
    }
}
