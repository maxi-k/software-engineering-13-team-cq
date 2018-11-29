package de.unia.se.teamcq.events.repository

import de.unia.se.teamcq.events.model.VehicleStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface VehicleStatusRepository : JpaRepository<VehicleStatus, Long>
