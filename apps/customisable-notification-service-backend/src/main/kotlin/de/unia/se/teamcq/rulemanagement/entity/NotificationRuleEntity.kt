package de.unia.se.teamcq.rulemanagement.entity

import de.unia.se.teamcq.notificationmanagement.entity.AggregatorEntity
import de.unia.se.teamcq.notificationmanagement.entity.RecipientEntity
import de.unia.se.teamcq.ruleevaluation.entity.RuleConditionEntity
import de.unia.se.teamcq.user.entity.UserEntity
import de.unia.se.teamcq.vehiclestate.entity.FleetReferenceEntity
import de.unia.se.teamcq.vehiclestate.entity.VehicleStateEntity
import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode
import java.io.Serializable
import java.sql.Timestamp
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToMany
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.OneToOne
import javax.persistence.OrderColumn
import javax.validation.constraints.NotNull

@Entity
// Constructor with (null)-default values for everything necessary for MapStruct
data class NotificationRuleEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var ruleId: Long? = null,

    @get: NotNull
    var name: String? = null,

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_owner")
    var owner: UserEntity? = null,

    var description: String? = null,

    @OneToOne(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    var condition: RuleConditionEntity? = null,

    @OneToOne(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    var aggregator: AggregatorEntity? = null,

    @OneToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL], targetEntity = RecipientEntity::class,
    orphanRemoval = true)
    // https://stackoverflow.com/questions/4334970/hibernate-cannot-simultaneously-fetch-multiple-bags
    @Fetch(value = FetchMode.SUBSELECT)
    var recipients: List<RecipientEntity>? = mutableListOf(),

    @get: NotNull
    var ownerAsAdditionalRecipient: Boolean? = null,

    @ManyToMany(fetch = FetchType.EAGER, cascade = [CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH])
    // https://stackoverflow.com/questions/4334970/hibernate-cannot-simultaneously-fetch-multiple-bags
    @Fetch(value = FetchMode.SUBSELECT)
    @OrderColumn
    var affectedFleets: List<FleetReferenceEntity>? = mutableListOf(),

    @ManyToMany(fetch = FetchType.LAZY, cascade = [CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH])
    // https://stackoverflow.com/questions/4334970/hibernate-cannot-simultaneously-fetch-multiple-bags
    @Fetch(value = FetchMode.SUBSELECT)
    var processedVehicleStates: Set<VehicleStateEntity>? = mutableSetOf(),

    @ManyToMany(fetch = FetchType.LAZY, cascade = [CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH])
    // https://stackoverflow.com/questions/4334970/hibernate-cannot-simultaneously-fetch-multiple-bags
    @Fetch(value = FetchMode.SUBSELECT)
    var matchedVehicleStatesNotYetSent: Set<VehicleStateEntity>? = mutableSetOf(),

    var affectingAllApplicableFleets: Boolean? = null,

    var lastUpdate: Timestamp? = null

) : Serializable
