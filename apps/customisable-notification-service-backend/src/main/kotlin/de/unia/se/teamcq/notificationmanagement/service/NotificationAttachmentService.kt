package de.unia.se.teamcq.notificationmanagement.service

import com.google.common.base.CaseFormat
import com.google.common.io.Closeables
import de.unia.se.teamcq.notificationmanagement.model.NotificationData
import de.unia.se.teamcq.ruleevaluation.model.IPredicateFieldProvider
import de.unia.se.teamcq.ruleevaluation.service.PredicateFieldContainer
import de.unia.se.teamcq.vehiclestate.model.VehicleState
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVPrinter
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.ByteArrayResource
import org.springframework.core.io.Resource
import org.springframework.stereotype.Component
import java.io.BufferedWriter
import java.io.ByteArrayOutputStream
import java.io.OutputStreamWriter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.SortedMap
import java.util.TimeZone

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

    private fun getDataTypesWithFields(): SortedMap<IPredicateFieldProvider, List<String>> {

        val predicateFieldProviders = predicateFieldContainer.getPredicateFieldProviders()

        return predicateFieldProviders.map { fieldProvider ->

            val fieldNamesOfProvider = fieldProvider.predicateFields.values.map { vehicleStateField ->
                vehicleStateField.fieldName!!
            }.sorted()

            fieldProvider to fieldNamesOfProvider
        }.toMap().toSortedMap(compareBy { fieldProvider -> fieldProvider.predicateFieldProviderName })
    }

    private fun getCsvColumnNames(dataTypesWithFields: Map<IPredicateFieldProvider, List<String>>): List<String> {

        // TODO: Consider using fleetReference.fetchVehicleData to retrieve more information
        // TODO: than the one below
        val vehicleStateFields = listOf("state_id", "vin")

        val vehicleStateDataTypeFieldNames = dataTypesWithFields.flatMap { (dataType, fieldNames) ->
            fieldNames.map { fieldName ->

                val formattedProviderName = CaseFormat.UPPER_CAMEL.to(
                        CaseFormat.LOWER_UNDERSCORE, dataType.predicateFieldProviderName
                )
                val formattedFieldName = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, fieldName)

                "${formattedProviderName}_$formattedFieldName"
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

        val predicateFieldValues = dataTypesWithFields.flatMap { (predicateProviderWithNextValues, fieldNames) ->

            val allVehicleStateDataTypes = vehicleState.vehicleStateDataTypes

            val matchingDataType = allVehicleStateDataTypes.firstOrNull { dataType ->
                dataType.predicateFieldProviderName == predicateProviderWithNextValues.predicateFieldProviderName
            }

            val fieldValues = fieldNames.map { predicateFieldInDataType ->

                val fieldValue = matchingDataType?.retrieveFieldValue(predicateFieldInDataType)

                applyDateFormatIfDate(fieldValue)
            }

            fieldValues
        }

        return vehicleStateFieldValues.plus(predicateFieldValues)
    }

    private fun applyDateFormatIfDate(fieldValue: Any?): Any? {
        return if (fieldValue is Date) {
            dateFormat.format(fieldValue)
        } else {
            fieldValue
        }
    }

    companion object {
        private val logger = LoggerFactory.getLogger(NotificationAttachmentService::class.java)

        // ISO 8601, see https://mincong-h.github.io/2017/02/16/convert-date-to-string-in-java/
        private val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }
    }
}
