package de.unia.se.teamcq.scheduling.job

import org.quartz.JobExecutionContext
import org.springframework.scheduling.quartz.QuartzJobBean

object JobTestUtils {

    // Can't test this without reflection because Kotlin interprets protected differently than Java and
    // Quartz jobs override a protected Quartz method
    fun invokeScheduledJobExecuteInternal(
        quartzJob: QuartzJobBean,
        jobExecutionContext: JobExecutionContext
    ) {

        val method = quartzJob.javaClass.getDeclaredMethod("executeInternal", JobExecutionContext::class.java)
        method.isAccessible = true
        method.invoke(quartzJob, jobExecutionContext)
    }
}
