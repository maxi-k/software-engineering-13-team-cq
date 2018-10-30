package de.unia.se.teamcq.calculator

import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class EvalController {

    @CrossOrigin(origins = arrayOf("*"))
    @GetMapping("/eval")
    fun eval(@RequestParam(value = "e") expression: String): EvalResult {
        return try {
            val result = Evaluator.parseAndEval(expression)
            EvalResult("success", expression, result.toString(), null)
        } catch (err: IllegalArgumentException) {
            err.printStackTrace()
            EvalResult("error", expression, null, "Invalid expression!")
        }
    }
}
