package de.unia.se.teamcq.notificationmanagement.mapping

import de.unia.se.teamcq.TestUtils.getTestAggregatorCountingDto
import de.unia.se.teamcq.TestUtils.getTestAggregatorCountingEntity
import de.unia.se.teamcq.TestUtils.getTestAggregatorCountingModel
import de.unia.se.teamcq.TestUtils.getTestAggregatorImmediatelyDto
import de.unia.se.teamcq.TestUtils.getTestAggregatorImmediatelyEntity
import de.unia.se.teamcq.TestUtils.getAggregatorImmediatelyModel
import de.unia.se.teamcq.TestUtils.getTestAggregatorScheduledDto
import de.unia.se.teamcq.TestUtils.getTestAggregatorScheduledEntity
import de.unia.se.teamcq.TestUtils.getTestAggregatorScheduledModel
import io.kotlintest.specs.StringSpec
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [(TestConfiguration::class)])
class AggregatorMapperTest : StringSpec() {

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

            notificationAggregatorMapper.modelToEntity(getTestAggregatorScheduledModel())
            verify(exactly = 1) {
                notificationAggregatorScheduledMapper.modelToEntity(any())
            }

            notificationAggregatorMapper.modelToEntity(getTestAggregatorCountingModel())
            verify(exactly = 1) {
                notificationAggregatorCountingdMapper.modelToEntity(any())
            }

            notificationAggregatorMapper.modelToEntity(getAggregatorImmediatelyModel())
            verify(exactly = 1) {
                notificationAggregatorImmediatelyMapper.modelToEntity(any())
            }
        }

        "Convert entity to model" {

            notificationAggregatorMapper.entityToModel(getTestAggregatorScheduledEntity())
            verify(exactly = 1) {
                notificationAggregatorScheduledMapper.entityToModel(any())
            }

            notificationAggregatorMapper.entityToModel(getTestAggregatorCountingEntity())
            verify(exactly = 1) {
                notificationAggregatorCountingdMapper.entityToModel(any())
            }

            notificationAggregatorMapper.entityToModel(getTestAggregatorImmediatelyEntity())
            verify(exactly = 1) {
                notificationAggregatorImmediatelyMapper.entityToModel(any())
            }
        }

        "Convert model to dto" {

            notificationAggregatorMapper.modelToDto(getTestAggregatorScheduledModel())
            verify(exactly = 1) {
                notificationAggregatorScheduledMapper.modelToDto(any())
            }

            notificationAggregatorMapper.modelToDto(getTestAggregatorCountingModel())
            verify(exactly = 1) {
                notificationAggregatorCountingdMapper.modelToDto(any())
            }

            notificationAggregatorMapper.modelToDto(getAggregatorImmediatelyModel())
            verify(exactly = 1) {
                notificationAggregatorImmediatelyMapper.modelToDto(any())
            }
        }

        "Convert dto to model" {

            notificationAggregatorMapper.dtoToModel(getTestAggregatorScheduledDto())
            verify(exactly = 1) {
                notificationAggregatorScheduledMapper.dtoToModel(any())
            }

            notificationAggregatorMapper.dtoToModel(getTestAggregatorCountingDto())
            verify(exactly = 1) {
                notificationAggregatorCountingdMapper.dtoToModel(any())
            }

            notificationAggregatorMapper.dtoToModel(getTestAggregatorImmediatelyDto())
            verify(exactly = 1) {
                notificationAggregatorImmediatelyMapper.dtoToModel(any())
            }
        }
    }
}
