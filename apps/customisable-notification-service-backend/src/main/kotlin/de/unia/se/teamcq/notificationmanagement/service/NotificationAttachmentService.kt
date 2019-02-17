package de.unia.se.teamcq.notificationmanagement.service

import de.unia.se.teamcq.notificationmanagement.model.NotificationData
import de.unia.se.teamcq.ruleevaluation.service.PredicateFieldContainer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.ByteArrayResource
import org.springframework.core.io.Resource
import org.springframework.stereotype.Component
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVPrinter
import java.io.BufferedWriter
import java.io.ByteArrayOutputStream
import java.io.OutputStreamWriter
import com.google.common.io.Closeables
import de.unia.se.teamcq.ruleevaluation.model.IPredicateFieldProvider
import de.unia.se.teamcq.vehiclestate.model.VehicleState
import org.slf4j.LoggerFactory

@Component
class NotificationAttachmentService : INotificationAttachmentService {

    @Autowired
    private lateinit var predicateFieldContainer: PredicateFieldContainer

    override fun getCsvAttachment(notificationData: NotificationData): Resource {

        val dataTypesWithFields = getDataTypesWithFields()

        val csvColumnNames = getCsvColumnNames(dataTypesWithFields)

        val byteArrayOutputStream = ByteArrayOutputStream()
        var bufferedWriter: BufferedWriter? = null
        var csvPrinter: CSVPrinter? = null

        var threwException = true

        try {

            bufferedWriter = BufferedWriter(OutputStreamWriter(byteArrayOutputStream))
            val csvFormat = CSVFormat.DEFAULT.withHeader(*csvColumnNames.toTypedArray())
            csvPrinter = CSVPrinter(bufferedWriter, csvFormat)

            notificationData.matchedVehicleStates.map { vehicleState ->
                val csvRecordValues = getCsvRecordValues(vehicleState, dataTypesWithFields)
                csvPrinter.printRecord(csvRecordValues)
            }

            threwException = false
        } catch (exception: Exception) {

            val ruleId = notificationData.notificationRule.ruleId
            logger.error("Error while writing Notification Attachment CSV for Rule $ruleId", exception)
        } finally {

            bufferedWriter!!.flush()
            Closeables.close(bufferedWriter, threwException)
            Closeables.close(csvPrinter, threwException)
        }

        return ByteArrayResource(byteArrayOutputStream.toByteArray())
    }

    private fun getDataTypesWithFields(): Map<IPredicateFieldProvider, List<String>> {

        val predicateFieldProviders = predicateFieldContainer.getPredicateFieldProviders()

        return predicateFieldProviders.map { fieldProvider ->

            val fieldNamesOfProvider = fieldProvider.predicateFields.values.map { vehicleStateField ->
                vehicleStateField.fieldName!!
            }
            fieldProvider to fieldNamesOfProvider
        }.toMap()
    }

    private fun getCsvColumnNames(dataTypesWithFields: Map<IPredicateFieldProvider, List<String>>): List<String> {

        // TODO: Consider using fleetReference.fetchVehicleData to retrieve more information
        // TODO: than the one below
        val vehicleStateFields = listOf("state_id", "vin")

        val vehicleStateDataTypeFieldNames = dataTypesWithFields.flatMap { (predicateFieldProvider, fieldNames) ->
            fieldNames.map { fieldName ->
                "${predicateFieldProvider.predicateFieldProviderName}_$fieldName"
            }
        }

        return vehicleStateFields.plus(vehicleStateDataTypeFieldNames)
    }

    private fun getCsvRecordValues(
        vehicleState: VehicleState,
        dataTypesWithFields: Map<IPredicateFieldProvider, List<String>>
    ): List<Any?> {

        val vehicleStateFieldValues = listOf(
                vehicleState.stateId,
                vehicleState.vehicleReference!!.vin!!
        )

        val predicateFieldValues = dataTypesWithFields.flatMap { (predicateFieldProvider, fieldNames) ->

            val allVehicleStateDataTypes = vehicleState.vehicleStateDataTypes

            val matchingDataType = allVehicleStateDataTypes.filter { dataType ->
                dataType.predicateFieldProviderName == predicateFieldProvider.predicateFieldProviderName
            }

            val fieldValues = matchingDataType.flatMap { dataType ->
                fieldNames.map { predicateFieldInDataType ->
                    dataType.retrieveFieldValue(predicateFieldInDataType)
                }
            }

            fieldValues
        }

        return vehicleStateFieldValues.plus(predicateFieldValues)
    }

    companion object {
        private val logger = LoggerFactory.getLogger(NotificationAttachmentService::class.java)
    }
}
