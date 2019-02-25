package de.unia.se.teamcq.notificationmanagement.service

import com.google.common.base.CaseFormat
import de.unia.se.teamcq.notificationmanagement.model.NotificationData
import de.unia.se.teamcq.ruleevaluation.service.PredicateFieldContainer
import de.unia.se.teamcq.vehiclestate.model.VehicleState
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVPrinter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.ByteArrayResource
import org.springframework.core.io.Resource
import org.springframework.stereotype.Component
import java.io.BufferedWriter
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.OutputStreamWriter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.SortedMap
import java.util.TimeZone

@Component
class NotificationAttachmentService : INotificationAttachmentService {

    @Autowired
    private lateinit var predicateFieldContainer: PredicateFieldContainer

    @Throws(IOException::class)
    override fun getCsvAttachment(notificationData: NotificationData): Resource {

        val dataTypesWithFields = getDataTypesWithFields()
        val csvColumnNames = getCsvColumnNames(dataTypesWithFields)

        val byteArrayOutputStream = ByteArrayOutputStream()
        val bufferedWriter = BufferedWriter(OutputStreamWriter(byteArrayOutputStream))

        val csvFormat = CSVFormat.DEFAULT.withHeader(*csvColumnNames.toTypedArray())
        val csvPrinter = CSVPrinter(bufferedWriter, csvFormat)

        bufferedWriter.use {
            csvPrinter.use {
                notificationData.matchedVehicleStates.map { vehicleState ->
                    val csvRecordValues = getCsvRecordValues(vehicleState, dataTypesWithFields)
                    csvPrinter.printRecord(csvRecordValues)
                }
            }
        }

        return ByteArrayResource(byteArrayOutputStream.toByteArray())
    }

    private fun getDataTypesWithFields(): SortedMap<String, List<String>> {

        val predicateFieldProviders = predicateFieldContainer.getPredicateFieldProviders()

        return predicateFieldProviders.map { fieldProvider ->

            val fieldNamesOfProvider = fieldProvider.predicateFields.values.map { vehicleStateField ->
                vehicleStateField.fieldName!!
            }.sorted()

            fieldProvider.predicateFieldProviderName to fieldNamesOfProvider
        }.toMap().toSortedMap()
    }

    private fun getCsvColumnNames(dataTypeNamesToFields: Map<String, List<String>>): List<String> {

        // TODO: Consider using fleetReference.fetchVehicleData to retrieve more information
        // TODO: than the one below, see #150
        val vehicleStateFields = listOf(STATE_ID_FIELD_NAME, VIN_FIELD_NAME, BRAND_FIELD_NAME,
                SERIES_FIELD_NAME, MODEL_FIELD_NAME, NOTE_FIELD_NAME)

        val vehicleStateDataTypeFieldNames = dataTypeNamesToFields.flatMap { (nameOfProviderWithField, fieldNames) ->
            fieldNames.map { fieldName ->
                val formattedProviderName = CaseFormat.UPPER_CAMEL.to(
                        CaseFormat.LOWER_UNDERSCORE, nameOfProviderWithField
                )
                val formattedFieldName = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, fieldName)

                "${formattedProviderName}_$formattedFieldName"
            }
        }

        return vehicleStateFields.plus(vehicleStateDataTypeFieldNames)
    }

    private fun getCsvRecordValues(
        vehicleState: VehicleState,
        dataTypeNamesToFields: Map<String, List<String>>
    ): List<Any?> {

        val vehicleStateFieldValues = listOf(
                vehicleState.stateId,
                vehicleState.vehicleReference?.vin,
                vehicleState.vehicleReference?.brand,
                vehicleState.vehicleReference?.series,
                vehicleState.vehicleReference?.model,
                vehicleState.vehicleReference?.note
        )

        val allVehicleStateDataTypes = vehicleState.vehicleStateDataTypes.map { dataType ->
            dataType.predicateFieldProviderName to dataType
        }.toMap()

        val predicateFieldValues = dataTypeNamesToFields.flatMap { (nameOfProviderWithField, fieldNames) ->

            val matchingDataType = allVehicleStateDataTypes[nameOfProviderWithField]

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
        private const val STATE_ID_FIELD_NAME = "state_id"
        private const val VIN_FIELD_NAME = "vin"
        private const val BRAND_FIELD_NAME = "brand"
        private const val MODEL_FIELD_NAME = "model"
        private const val SERIES_FIELD_NAME = "series"
        private const val NOTE_FIELD_NAME = "note"

        // ISO 8601, see https://mincong-h.github.io/2017/02/16/convert-date-to-string-in-java/
        private val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }
    }
}
