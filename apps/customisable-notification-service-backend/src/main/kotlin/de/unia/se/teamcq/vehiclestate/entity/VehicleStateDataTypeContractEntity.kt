package de.unia.se.teamcq.vehiclestate.entity

import java.io.Serializable
import javax.persistence.Entity
import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode
import javax.validation.constraints.NotNull
import javax.persistence.ElementCollection
import javax.persistence.Column

@Entity
data class VehicleStateDataTypeContractEntity(

    @get: NotNull
    var duePerWeek: Int? = null,

    @ElementCollection
    //@CollectionTable(name = "contract_vins", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "vin")
    var vins: Set<String>? = hashSetOf<String>(),

    @get: NotNull
    var calendarWeek: Int? = null

) : VehicleStateDataTypeEntity() 
