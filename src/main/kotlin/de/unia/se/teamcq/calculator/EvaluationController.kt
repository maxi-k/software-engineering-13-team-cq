package de.unia.se.teamcq.calculator

import de.unia.se.teamcq.rule.management.mapping.NotificationRuleMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class EvaluationController {

    @Autowired
    lateinit var evaluationService: EvaluationService

    @Autowired
    lateinit var notificationRuleMapper: NotificationRuleMapper

    @CrossOrigin(origins = arrayOf("*"))
    @GetMapping("/eval")
    fun eval(@RequestParam(value = "e") expression: String): EvaluationResult {
        return try {
            val result = evaluationService.parseAndEvaluate(expression)
            EvaluationResult("success", expression, result.toString(), null)
        } catch (exception: IllegalArgumentException) {
            exception.printStackTrace()
            EvaluationResult("error", expression, null, "Invalid expression!")
        }
    }
}
