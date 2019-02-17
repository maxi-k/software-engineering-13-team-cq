package de.unia.se.teamcq.notificationmanagement.service

import de.unia.se.teamcq.TestUtils.getTestNotificationDataModel
import de.unia.se.teamcq.TestUtils.getTestPredicateFieldProviders
import de.unia.se.teamcq.ruleevaluation.service.PredicateFieldContainer
import io.kotlintest.matchers.string.shouldContain
import io.kotlintest.should
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

                csvText.shouldContain("charge")
            }
        }
    }
}
