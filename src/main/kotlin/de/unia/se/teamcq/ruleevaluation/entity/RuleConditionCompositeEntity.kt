package de.unia.se.teamcq.ruleevaluation.entity

import de.unia.se.teamcq.ruleevaluation.model.LogicalConnectiveType
import java.io.Serializable
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.OneToMany
import javax.validation.constraints.NotNull

@Entity
class RuleConditionCompositeEntity(

    conditionId: Long? = 0,

    @get: NotNull
    var logicalConnective: LogicalConnectiveType?,

    @get: NotNull
    @OneToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL], targetEntity = RuleConditionEntity::class)
    var subConditions: List<RuleConditionEntity>

) : RuleConditionEntity(conditionId), Serializable {
    // Necessary for MapStruct
    constructor() : this(null, null, mutableListOf<RuleConditionEntity>())
}
