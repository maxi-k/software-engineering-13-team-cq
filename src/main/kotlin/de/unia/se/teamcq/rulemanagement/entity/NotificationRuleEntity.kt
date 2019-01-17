package de.unia.se.teamcq.rulemanagement.entity

import de.unia.se.teamcq.ruleevaluation.entity.RuleConditionEntity
import de.unia.se.teamcq.user.entity.UserEntity
import java.io.Serializable
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToOne
import javax.validation.constraints.NotNull

@Entity
data class NotificationRuleEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var ruleId: Long? = 0,

    @get: NotNull
    var name: String?,

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_owner")
    var owner: UserEntity?,

    var description: String?,

    @OneToOne(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    var condition: RuleConditionEntity?

) : Serializable {
    // Necessary for MapStruct
    constructor() : this(null, null, null, null, null)
}
