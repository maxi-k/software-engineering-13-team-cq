package de.unia.se.teamcq.calculator

import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.atomic.AtomicLong


@RestController
class EvalController {
    @CrossOrigin(origins = arrayOf("*"))
    @GetMapping("/eval")
    fun eval(@RequestParam(value = "e") e: String) : EvalResult {
        try {
            val result = eval(parse(e))
            return EvalResult("success", e, result.toString(), null)
        } catch (err: IllegalArgumentException) {
            err.printStackTrace()
            return EvalResult("error", e, null, "invalid expression")
        }
    }

}
