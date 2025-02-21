package org.prodcontest.controllers

import jakarta.validation.Valid
import org.prodcontest.requests.AdClickRequest
import org.prodcontest.responses.AdResponse
import org.prodcontest.services.CampaignService
import org.prodcontest.services.ClickService
import org.prodcontest.services.ClientService
import org.prodcontest.services.ImpressionService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/ads")
class AdController(
    private val clickService: ClickService,
    private val campaignService: CampaignService,
    private val clientService: ClientService,
    private val impressionService: ImpressionService
) {
    @GetMapping
    fun get(@RequestParam(required = true, name = "client_id") clientId: UUID): ResponseEntity<AdResponse> {
        val client = clientService.getById(clientId)
        val ad = campaignService
            .findRelevantAd(client)

        impressionService.create(ad, client)

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ad.toAdResponse())
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

        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .build()
    }
}