package de.unia.se.teamcq.notificationmanagement.mapping

import de.unia.se.teamcq.TestUtils.getTestNotificationAggregatorCountingDto
import de.unia.se.teamcq.TestUtils.getTestNotificationAggregatorCountingEntity
import de.unia.se.teamcq.TestUtils.getTestNotificationAggregatorCountingModel
import de.unia.se.teamcq.TestUtils.getTestNotificationAggregatorImmediatelyDto
import de.unia.se.teamcq.TestUtils.getTestNotificationAggregatorImmediatelyEntity
import de.unia.se.teamcq.TestUtils.getTestNotificationAggregatorImmediatelyModel
import de.unia.se.teamcq.TestUtils.getTestNotificationAggregatorScheduledDto
import de.unia.se.teamcq.TestUtils.getTestNotificationAggregatorScheduledEntity
import de.unia.se.teamcq.TestUtils.getTestNotificationAggregatorScheduledModel
import io.kotlintest.specs.StringSpec
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [(TestConfiguration::class)])
class NotificationAggregatorMapperTest : StringSpec() {

    @MockK(relaxed = true)
    lateinit var notificationAggregatorScheduledMapper: INotificationAggregatorScheduledMapper

    @MockK(relaxed = true)
    lateinit var notificationAggregatorCountingdMapper: INotificationAggregatorCountingMapper

    @MockK(relaxed = true)
    lateinit var notificationAggregatorImmediatelyMapper: INotificationAggregatorImmediatetlyMapper

    @InjectMockKs
    lateinit var notificationAggregatorMapper: NotificationAggregatorMapper

    init {
        MockKAnnotations.init(this)

        "Convert model to entity" {

            notificationAggregatorMapper.modelToEntity(getTestNotificationAggregatorScheduledModel())
            verify(exactly = 1) {
                notificationAggregatorScheduledMapper.modelToEntity(any())
            }

            notificationAggregatorMapper.modelToEntity(getTestNotificationAggregatorCountingModel())
            verify(exactly = 1) {
                notificationAggregatorCountingdMapper.modelToEntity(any())
            }

            notificationAggregatorMapper.modelToEntity(getTestNotificationAggregatorImmediatelyModel())
            verify(exactly = 1) {
                notificationAggregatorImmediatelyMapper.modelToEntity(any())
            }
        }

        "Convert entity to model" {

            notificationAggregatorMapper.entityToModel(getTestNotificationAggregatorScheduledEntity())
            verify(exactly = 1) {
                notificationAggregatorScheduledMapper.entityToModel(any())
            }

            notificationAggregatorMapper.entityToModel(getTestNotificationAggregatorCountingEntity())
            verify(exactly = 1) {
                notificationAggregatorCountingdMapper.entityToModel(any())
            }

            notificationAggregatorMapper.entityToModel(getTestNotificationAggregatorImmediatelyEntity())
            verify(exactly = 1) {
                notificationAggregatorImmediatelyMapper.entityToModel(any())
            }
        }

        "Convert model to dto" {

            notificationAggregatorMapper.modelToDto(getTestNotificationAggregatorScheduledModel())
            verify(exactly = 1) {
                notificationAggregatorScheduledMapper.modelToDto(any())
            }

            notificationAggregatorMapper.modelToDto(getTestNotificationAggregatorCountingModel())
            verify(exactly = 1) {
                notificationAggregatorCountingdMapper.modelToDto(any())
            }

            notificationAggregatorMapper.modelToDto(getTestNotificationAggregatorImmediatelyModel())
            verify(exactly = 1) {
                notificationAggregatorImmediatelyMapper.modelToDto(any())
            }
        }

        "Convert dto to model" {

            notificationAggregatorMapper.dtoToModel(getTestNotificationAggregatorScheduledDto())
            verify(exactly = 1) {
                notificationAggregatorScheduledMapper.dtoToModel(any())
            }

            notificationAggregatorMapper.dtoToModel(getTestNotificationAggregatorCountingDto())
            verify(exactly = 1) {
                notificationAggregatorCountingdMapper.dtoToModel(any())
            }

            notificationAggregatorMapper.dtoToModel(getTestNotificationAggregatorImmediatelyDto())
            verify(exactly = 1) {
                notificationAggregatorImmediatelyMapper.dtoToModel(any())
            }
        }
    }
}
