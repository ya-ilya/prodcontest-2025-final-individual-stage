package org.prodcontest.controllers

import jakarta.validation.Valid
import org.prodcontest.requests.TimeAdvanceRequest
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/time")
class TimeController {
    @PostMapping("/advance")
    fun advance(@Valid @RequestBody request: TimeAdvanceRequest) {
        TODO()
    }
}