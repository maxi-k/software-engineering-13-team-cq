package de.unia.se.teamcq.rule.management.entity

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
// import javax.transaction.Transactional

@Repository
interface NotificationRuleEntityRepository : JpaRepository<NotificationRuleEntity, Long>
