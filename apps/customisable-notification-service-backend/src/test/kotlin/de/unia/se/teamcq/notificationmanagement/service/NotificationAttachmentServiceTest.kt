package de.unia.se.teamcq.notificationmanagement.service

import de.unia.se.teamcq.TestUtils.getTestNotificationDataModel
import de.unia.se.teamcq.TestUtils.getTestPredicateFieldProviders
import de.unia.se.teamcq.TestUtils.getTestVehicleStateDataTypeEngineModel
import de.unia.se.teamcq.notificationmanagement.model.NotificationData
import de.unia.se.teamcq.ruleevaluation.service.PredicateFieldContainer
import de.unia.se.teamcq.vehiclestate.model.VehicleReference
import de.unia.se.teamcq.vehiclestate.model.VehicleState
import io.kotlintest.should
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.test.context.ContextConfiguration
import org.springframework.util.FileCopyUtils

@ContextConfiguration(classes = [TestConfiguration::class])
class NotificationAttachmentServiceTest : StringSpec() {

    @MockK
    private lateinit var predicateFieldContainer: PredicateFieldContainer

    @InjectMockKs
    private lateinit var notificationAttachmentService: NotificationAttachmentService

    init {
        MockKAnnotations.init(this)

        "GetCsvAttachment" should {

            "Generate valid CSVs" {

                every { predicateFieldContainer.getPredicateFieldProviders() } returns
                        getTestPredicateFieldProviders()

                val notificationData = getTestNotificationDataModel()

                val csvResource = notificationAttachmentService.getCsvAttachment(notificationData)

                val csvContent = FileCopyUtils.copyToString(csvResource.inputStream.bufferedReader())

                val expectedCsvContext = """state_id,vin,brand,series,model,note,battery_charge,battery_charging_status,battery_remaining_charging_hours,battery_remaining_range,battery_voltage,engine_capacity,engine_charging_status,engine_power,engine_transmission_type,service_due_date,service_remaining_days,service_remaining_mileage,service_status
                           |0,UUID456,BMW,Some Series,Some Model,Some note,0.5,OK,10,5,0.699999988079071,120,Gas,120,Some,1970-01-18T22:54:10.098+01:00,20,15,OK
                           |1,UUID456,BMW,Some Series,Some Model,Some note,0.5,OK,10,5,0.699999988079071,120,Gas,120,Some,1970-01-18T22:54:10.098+01:00,20,15,OK
                           |2,UUID456,BMW,Some Series,Some Model,Some note,0.5,OK,10,5,0.699999988079071,120,Gas,120,Some,1970-01-18T22:54:10.098+01:00,20,15,OK
                           |""".trimMargin()

                val systemLineSeparator = System.getProperty("line.separator")
                val formattedExpectedCsvContent = expectedCsvContext.replace(systemLineSeparator, "\r\n")

                csvContent shouldBe formattedExpectedCsvContent
            }

            "Generate valid CSVs even if VehicleStates have different VehicleStateDataTypes" {

                every { predicateFieldContainer.getPredicateFieldProviders() } returns
                        getTestPredicateFieldProviders()

                val notificationData = getTestNotificationDataModel()

                val modifiedNotificationData = getNotificationDataWithStateWithMissingDataType(notificationData)

                val csvResource = notificationAttachmentService.getCsvAttachment(modifiedNotificationData)

                val csvContent = FileCopyUtils.copyToString(csvResource.inputStream.bufferedReader())

                val expectedCsvContext = """state_id,vin,brand,series,model,note,battery_charge,battery_charging_status,battery_remaining_charging_hours,battery_remaining_range,battery_voltage,engine_capacity,engine_charging_status,engine_power,engine_transmission_type,service_due_date,service_remaining_days,service_remaining_mileage,service_status
                           |0,UUID456,BMW,Some Series,Some Model,Some note,0.5,OK,10,5,0.699999988079071,120,Gas,120,Some,1970-01-18T22:54:10.098+01:00,20,15,OK
                           |1,UUID456,BMW,Some Series,Some Model,Some note,0.5,OK,10,5,0.699999988079071,120,Gas,120,Some,1970-01-18T22:54:10.098+01:00,20,15,OK
                           |2,UUID456,BMW,Some Series,Some Model,Some note,0.5,OK,10,5,0.699999988079071,120,Gas,120,Some,1970-01-18T22:54:10.098+01:00,20,15,OK
                           |3,2,,,,,,,,,,120,Gas,120,Some,,,,
                           |""".trimMargin()

                val systemLineSeparator = System.getProperty("line.separator")
                val formattedExpectedCsvContent = expectedCsvContext.replace(systemLineSeparator, "\r\n")

                csvContent shouldBe formattedExpectedCsvContent
            }
        }
    }

    private fun getNotificationDataWithStateWithMissingDataType(notificationData: NotificationData): NotificationData {
        return NotificationData(notificationData.notificationRule,
                notificationData.matchedVehicleStates.plus(
                        VehicleState(
                                3,
                                VehicleReference("2"),
                                vehicleStateDataTypes = setOf(getTestVehicleStateDataTypeEngineModel())
                        )
                )
        )
    }
}
