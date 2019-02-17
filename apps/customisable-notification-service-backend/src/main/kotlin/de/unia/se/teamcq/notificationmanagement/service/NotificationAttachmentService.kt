package de.unia.se.teamcq.notificationmanagement.service

import de.unia.se.teamcq.notificationmanagement.model.NotificationData
import de.unia.se.teamcq.ruleevaluation.service.PredicateFieldContainer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.ByteArrayResource
import org.springframework.core.io.Resource
import org.springframework.stereotype.Component
import java.io.IOException
import java.util.Arrays.asList
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVPrinter
import java.io.BufferedWriter
import java.io.ByteArrayOutputStream
import java.io.OutputStreamWriter

@Component
class NotificationAttachmentService : INotificationAttachmentService {

    @Autowired
    private lateinit var predicateFieldContainer: PredicateFieldContainer

    override fun getCsvAttachment(notificationData: NotificationData): Resource {

        val vehicleStateFields = listOf("state_id", "vin")
        // TODO: Consider using fleetReference.fetchVehicleData to retrieve additional information

        val vehicleStateDataTypeFields = predicateFieldContainer
                .getPredicateFieldProviders().map { fieldProvider ->
                    fieldProvider to fieldProvider.predicateFields.values.map { vehicleStateField ->
                        vehicleStateField.fieldName!!
                    }
                }

        val vehicleStateDataTypeFieldNames = vehicleStateDataTypeFields.flatMap { dataTypeFields ->
            dataTypeFields.second.map { fieldName ->
                "${dataTypeFields.first.predicateFieldProviderName}_$fieldName"
            }
        }

        val allCsvFieldNames = vehicleStateFields.plus(vehicleStateDataTypeFieldNames)

        val byteArrayOutputStream = ByteArrayOutputStream()
        var bufferedWriter: BufferedWriter? = null
        var csvPrinter: CSVPrinter? = null

        try {
            bufferedWriter = BufferedWriter(OutputStreamWriter(byteArrayOutputStream))
            csvPrinter = CSVPrinter(bufferedWriter, CSVFormat.DEFAULT
                    .withHeader(*allCsvFieldNames.toTypedArray())
            )

            notificationData.matchedVehicleStates.map { vehicleState ->

                val vehicleStateFieldValues = asList(
                        vehicleState.stateId,
                        vehicleState.vehicleReference!!.vin!!
                )

                val predicateFieldValues = vehicleStateDataTypeFields.flatMap { dataType ->

                    vehicleState.vehicleStateDataTypes.filter { potentiallyOfTypeDataType ->
                        potentiallyOfTypeDataType.predicateFieldProviderName == dataType.first.predicateFieldProviderName
                    }.flatMap { dataTypeInVehicleState ->
                        dataType.second.map { predicateFieldInDataType ->
                            dataTypeInVehicleState.retrieveFieldValue(predicateFieldInDataType)
                        }
                    }
                }

                val allRecordValues = vehicleStateFieldValues.plus(predicateFieldValues)

                csvPrinter.printRecord(allRecordValues)
            }

            println("Write CSV successfully!")
        } catch (e: Exception) {
            println("Writing CSV error!")
            e.printStackTrace()
        } finally {
            try {
                bufferedWriter!!.flush()
                bufferedWriter.close()
                csvPrinter!!.close()
            } catch (iOException: IOException) {
                println("Flushing/closing error!")
                iOException.printStackTrace()
            }
        }

        return ByteArrayResource(byteArrayOutputStream.toByteArray())
    }
}
