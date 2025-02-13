package org.prodcontest.controllers

import jakarta.validation.Valid
import org.prodcontest.requests.MLScoreRequest
import org.prodcontest.services.AdvertiserService
import org.prodcontest.services.ClientService
import org.prodcontest.services.MLScoreService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/ml-scores")
class MLScoresController(
    private val mlScoreService: MLScoreService,
    private val clientService: ClientService,
    private val advertiserService: AdvertiserService
) {
    @PostMapping
    fun createOrUpdate(@Valid @RequestBody request: MLScoreRequest) {
        mlScoreService.createOrUpdate(
            clientService.getById(request.clientId),
            advertiserService.getById(request.advertiserId),
            request.score
        )
    }
}