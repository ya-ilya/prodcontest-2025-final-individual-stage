package org.prodcontest.controllers

import jakarta.validation.Valid
import org.prodcontest.requests.AdvertiserUpsertRequest
import org.prodcontest.responses.AdvertiserResponse
import org.prodcontest.services.AdvertiserService
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
    fun bulk(@Valid @RequestBody request: List<AdvertiserUpsertRequest>) {
        request.forEach {
            advertiserService.createOrUpdate(
                it.advertiserId,
                it.name
            )
        }
    }
}