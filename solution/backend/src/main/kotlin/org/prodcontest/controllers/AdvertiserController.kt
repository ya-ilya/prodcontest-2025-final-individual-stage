package org.prodcontest.controllers

import jakarta.validation.Valid
import org.prodcontest.requests.AdvertiserUpsertRequest
import org.prodcontest.requests.MLScoreRequest
import org.prodcontest.responses.AdvertiserResponse
import org.prodcontest.services.AdvertiserService
import org.prodcontest.services.ClientService
import org.prodcontest.services.MLScoreService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
class AdvertiserController(
    private val advertiserService: AdvertiserService,
    private val mlScoreService: MLScoreService,
    private val clientService: ClientService
) {
    @GetMapping("/advertisers/{id}")
    fun getById(@PathVariable id: UUID): AdvertiserResponse {
        return advertiserService.getById(id).toResponse()
    }

    @PostMapping("/advertisers/bulk")
    fun bulk(@Valid @RequestBody request: List<AdvertiserUpsertRequest>): ResponseEntity<Any> {
        return ResponseEntity(request.map {
            advertiserService.createOrUpdate(
                it.advertiserId,
                it.name
            ).toResponse()
        }, HttpStatus.CREATED)
    }

    @PostMapping("/ml-scores")
    fun mlScore(@Valid @RequestBody request: MLScoreRequest) {
        mlScoreService.createOrUpdate(
            clientService.getById(request.clientId),
            advertiserService.getById(request.advertiserId),
            request.score
        )
    }
}