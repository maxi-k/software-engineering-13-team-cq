package de.unia.se.teamcq.user.entity

import de.unia.se.teamcq.user.model.UserNotificationType
import java.io.Serializable
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class UserSettingsEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = 0,

    var userNotificationType: UserNotificationType?

) : Serializable {
    constructor(): this(null, null)
}
