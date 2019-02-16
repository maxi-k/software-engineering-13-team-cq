package de.unia.se.teamcq.vehiclestate.entity

import java.io.Serializable
import javax.persistence.Entity
import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode
import javax.validation.constraints.NotNull

@Entity
data class VehicleStateDataTypeContractEntity(

    @get: NotNull
    var duePerWeek: Int? = null,

    @ElementCollection
    //@CollectionTable(name = "contract_vins", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "vin")
    private Set<String> vins = new HashSet<>();

    @get: NotNull
    var calendarWeek: Int? = null

) : VehicleStateDataTypeEntity() 
