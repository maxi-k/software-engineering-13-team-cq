package de.unia.se.teamcq.vehiclestate.entity

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface VehicleStateEntityRepository : JpaRepository<VehicleStateEntity, Long>
