package de.unia.se.teamcq.ruleevaluation.entity

import java.io.Serializable
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
abstract class RuleConditionEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var conditionId: Long? = 0

) : Serializable
