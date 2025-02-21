package org.prodcontest.controllers

import jakarta.validation.Valid
import org.prodcontest.requests.TimeAdvanceRequest
import org.prodcontest.responses.TimeAdvanceResponse
import org.prodcontest.services.DateService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/time")
class TimeController(private val dateService: DateService) {
    @PostMapping("/advance")
    fun advance(@Valid @RequestBody request: TimeAdvanceRequest): TimeAdvanceResponse {
        dateService.setCurrentDate(request.currentDate)
        return TimeAdvanceResponse(request.currentDate)
    }
}