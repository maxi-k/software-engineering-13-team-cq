package de.unia.se.teamcq.notificationmanagement.service

import de.unia.se.teamcq.notificationmanagement.model.AggregatorCounting
import de.unia.se.teamcq.notificationmanagement.model.AggregatorImmediate
import de.unia.se.teamcq.notificationmanagement.model.AggregatorScheduled
import de.unia.se.teamcq.notificationmanagement.model.NotificationData
import de.unia.se.teamcq.notificationmanagement.model.RecipientMail
import de.unia.se.teamcq.notificationmanagement.model.RecipientSms
import de.unia.se.teamcq.rulemanagement.entity.INotificationRuleRepository
import de.unia.se.teamcq.rulemanagement.model.NotificationRule
import de.unia.se.teamcq.user.model.UserNotificationType
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class NotificationService : INotificationService {

    @Autowired
    private lateinit var notificationTextService: INotificationTextService

    @Autowired
    private lateinit var notificationAttachmentService: INotificationAttachmentService

    @Autowired
    private lateinit var notificationMESAdapter: INotificationMESAdapter

    @Autowired
    private lateinit var notificationRuleRepository: INotificationRuleRepository

    override fun sendNotificationForRuleIfNecessary(notificationRule: NotificationRule) {

        val vehicleStateMatches = notificationRuleRepository.getVehicleStateMatchesForRule(
                notificationRule.ruleId!!
        )
        val notificationData = NotificationData(notificationRule, vehicleStateMatches)
        val notificationCount = vehicleStateMatches.count()

        val aggregator = notificationRule.aggregator!!
        when (aggregator) {
            is AggregatorScheduled -> sendNotification(notificationData)
            is AggregatorImmediate -> {
                if (notificationCount > 0) {
                    sendNotification(notificationData)
                }
            }
            is AggregatorCounting -> {
                val countThreshold = aggregator.notificationCountThreshold!!
                if (notificationCount >= countThreshold) {
                    sendNotification(notificationData)
                }
            }
            else -> logger.error(
                        "Unknown aggregator type encountered in rule {}: {}",
                        notificationRule.ruleId,
                        aggregator
                    )
        }
    }

    private fun sendNotification(notificationData: NotificationData) {
        val notificationRule = notificationData.notificationRule

        val emailRecipients = getEmailRecipients(notificationRule)

        val smsRecipients = getSmsRecipients(notificationRule)

        val notificationText = notificationTextService.getHtmlMailTextForNotification(notificationData)

        val attachment = notificationAttachmentService.getCsvAttachment(notificationData)

        emailRecipients.forEach { recipient ->
            notificationMESAdapter.sendNotification(
                    recipient,
                    "CNS: ${notificationRule.name}",
                    notificationText,
                    attachment
            )
        }
        // The MES_Swagger file only contains an EmailAPI
        // Given a SmsAPI, sending SMS can be added here easily

        notificationRuleRepository.deleteAllVehicleStateMatchesForRule(notificationRule.ruleId!!)
        logger.info("Sending notifications for scheduled rule with ID {}", notificationRule.ruleId)
    }

    private fun getSmsRecipients(notificationRule: NotificationRule): List<String> {
        var smsRecipients = notificationRule.recipients.filter { recipient ->
            recipient is RecipientSms
        }.map { recipient ->
            recipient as RecipientSms
            recipient.phoneNumber
        }

        if (notificationRule.ownerAsAdditionalRecipient!! &&
                notificationRule.owner?.userSettings?.userNotificationType == UserNotificationType.SMS) {
            smsRecipients = smsRecipients.plus(notificationRule.owner?.cellPhoneNumber)
        }

        return smsRecipients.filterNotNull()
    }

    private fun getEmailRecipients(notificationRule: NotificationRule): List<String> {

        var emailRecipients = notificationRule.recipients.filter { recipient ->
            recipient is RecipientMail
        }.map { recipient ->
            recipient as RecipientMail
            recipient.mailAddress
        }

        if (notificationRule.ownerAsAdditionalRecipient!! &&
                notificationRule.owner?.userSettings?.userNotificationType == UserNotificationType.EMAIL) {
            emailRecipients = emailRecipients.plus(notificationRule.owner?.mailAddress)
        }
        return emailRecipients.filterNotNull()
    }

    override fun storeNotificationData(notificationData: NotificationData) {
        val ruleId = notificationData.notificationRule.ruleId!!
        notificationData.matchedVehicleStates.forEach { vehicleState ->
            notificationRuleRepository.addVehicleStateMatchForRule(ruleId, vehicleState)
        }
        logger.info("Stored notificationData for rule with ID {}", notificationData.notificationRule.ruleId)
    }

    companion object {
        private val logger = LoggerFactory.getLogger(NotificationService::class.java)
    }
}
