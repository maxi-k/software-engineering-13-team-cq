package de.unia.se.teamcq.vehiclestate.service

import org.springframework.stereotype.Service
import org.springframework.web.client.RestClientException
import de.unia.se.teamcq.vehiclestate.model.VehicleState
import de.unia.se.teamcq.rulemanagement.model.NotificationRule

/**
 * The service that handles importing new VehicleStateData.
 */
@Service
interface IVehicleStateService {

    /**
     * Imports new data
     *
     * Imports new data into our DB that isn't already present.
     */
    @Throws(RestClientException::class, NullPointerException::class)
    fun importNewVehicleData()

    /**
     * Gets all [VehicleState]s not yet processed by a [NotificationRule]
     *
     * @param notificationRule The [NotificationRule] to get [VehicleState]s for
     * @return The [VehicleState]s to process
     */
    fun getUnprocessedVehicleStateForRule(notificationRule: NotificationRule): List<VehicleState>

    /**
     * Marks [VehicleState]s as processed by a [NotificationRule]
     *
     * @param notificationRule The [NotificationRule] that processed [VehicleState]s
     * @param vehicleStates The [VehicleState]s that got processed
     */
    fun markVehicleStateAsProcessedByRule(
        notificationRule: NotificationRule,
        vehicleStates: List<VehicleState>
    )
}
