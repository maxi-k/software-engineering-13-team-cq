package de.unia.se.teamcq.notificationmanagement.service

import de.unia.se.teamcq.TestUtils.getTestNotificationDataModel
import de.unia.se.teamcq.TestUtils.getTestPredicateFieldProviders
import de.unia.se.teamcq.ruleevaluation.service.PredicateFieldContainer
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

        "getCsvAttachment" should {

            "Generate a valid csv" {

                every { predicateFieldContainer.getPredicateFieldProviders() } returns
                        getTestPredicateFieldProviders()

                val notificationData = getTestNotificationDataModel()

                val csvResource = notificationAttachmentService.getCsvAttachment(notificationData)

                val csvText = FileCopyUtils.copyToString(csvResource.inputStream.bufferedReader())

                val expectedCsvContext = """|state_id,vin,battery_charge,battery_status,battery_voltage,engine_capacity,engine_fuel_type,engine_power,service_brake_fluid,service_due_date,service_status
                           |0,UUID456,0.5,Healthy,0.7,120,Gas,120,Fine,1970-01-18T21:54:10.098Z,Healthy
                           |0,UUID456,0.5,Healthy,0.7,120,Gas,120,Fine,1970-01-18T21:54:10.098Z,Healthy
                           |0,UUID456,0.5,Healthy,0.7,120,Gas,120,Fine,1970-01-18T21:54:10.098Z,Healthy
                           |""".trimMargin()

                val systemLineSeparator = System.getProperty("line.separator")
                val formattedExpectedCsvContent = expectedCsvContext.replace(systemLineSeparator, "\r\n")

                csvText shouldBe formattedExpectedCsvContent
            }
        }
    }
}
