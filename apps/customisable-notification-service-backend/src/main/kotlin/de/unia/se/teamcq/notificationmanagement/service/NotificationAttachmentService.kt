package de.unia.se.teamcq.notificationmanagement.service

import de.unia.se.teamcq.notificationmanagement.model.NotificationData
import de.unia.se.teamcq.ruleevaluation.service.PredicateFieldContainer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.ByteArrayResource
import org.springframework.core.io.Resource
import org.springframework.stereotype.Component

@Component
class NotificationAttachmentService : INotificationAttachmentService {

    @Autowired
    private lateinit var predicateFieldContainer: PredicateFieldContainer

    override fun getCsvAttachment(notificationData: NotificationData): Resource {

        val vehicleStateFields = listOf("state_id", "vin")
        // TODO: Consider using fleetReference.fetchVehicleData to retrieve additional information

        val vehicleStateDataTypeFields = predicateFieldContainer
            .getPredicateFieldProviders().flatMap { fieldProvider ->
                    fieldProvider.predicateFields.values.map { vehicleStateField ->
                    vehicleStateField.fieldName
                }
            }

        return ByteArrayResource(vehicleStateDataTypeFields.joinToString(",").toByteArray())
    }
}
