package org.prodcontest.controllers

import jakarta.validation.Valid
import org.prodcontest.requests.AdClickRequest
import org.prodcontest.responses.AdResponse
import org.prodcontest.services.CampaignService
import org.prodcontest.services.ClickService
import org.prodcontest.services.ClientService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/ads")
class AdsController(
    private val clickService: ClickService,
    private val campaignService: CampaignService,
    private val clientService: ClientService
) {
    @GetMapping
    fun get(@RequestParam(required = true, name = "client_id") clientId: UUID): AdResponse {
        return campaignService
            .findAd(clientService.getById(clientId))
            .random()
            .toAdResponse()
    }

    @PostMapping("/{adId}/click")
    fun click(
        @PathVariable adId: UUID,
        @Valid @RequestBody request: AdClickRequest
    ): ResponseEntity<Any> {
        clickService.create(
            campaignService.getById(adId),
            clientService.getById(request.clientId)
        )

        return ResponseEntity(HttpStatus.NO_CONTENT)
    }
}