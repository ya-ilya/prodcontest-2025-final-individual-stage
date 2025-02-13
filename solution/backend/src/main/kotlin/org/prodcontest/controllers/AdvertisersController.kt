package org.prodcontest.controllers

import jakarta.validation.Valid
import org.prodcontest.requests.AdvertiserUpsertRequest
import org.prodcontest.responses.AdvertiserResponse
import org.prodcontest.services.AdvertiserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/advertisers")
class AdvertisersController(private val advertiserService: AdvertiserService) {
    @GetMapping("/{id}")
    fun getById(@PathVariable id: UUID): AdvertiserResponse {
        return advertiserService.getById(id).toResponse()
    }

    @PostMapping("/bulk")
    fun bulk(@Valid @RequestBody request: List<AdvertiserUpsertRequest>): ResponseEntity<Any> {
        return ResponseEntity(request.map {
            advertiserService.createOrUpdate(
                it.advertiserId,
                it.name
            ).toResponse()
        }, HttpStatus.CREATED)
    }
}