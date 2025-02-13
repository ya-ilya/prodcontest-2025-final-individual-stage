package org.prodcontest.controllers

import jakarta.validation.Valid
import org.prodcontest.requests.CampaignCreateRequest
import org.prodcontest.requests.CampaignUpdateRequest
import org.prodcontest.responses.CampaignResponse
import org.prodcontest.services.AdvertiserService
import org.prodcontest.services.CampaignService
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.util.*

@RestController
@RequestMapping("/advertisers/{advertiserId}/campaigns")
class CampaignsController(
    private val campaignService: CampaignService,
    private val advertiserService: AdvertiserService
) {
    @PostMapping
    fun create(
        @PathVariable advertiserId: UUID,
        @Valid @RequestBody request: CampaignCreateRequest
    ): CampaignResponse {
        return campaignService.create(
            advertiserService.getById(advertiserId),
            request.impressionsLimit,
            request.clicksLimit,
            request.costPerImpression,
            request.costPerClick,
            request.adTitle,
            request.adText,
            request.startDate,
            request.endDate,
            request.targeting
        ).toResponse()
    }

    @GetMapping
    fun get(
        @PathVariable advertiserId: UUID,
        @RequestParam(value = "size", required = false, defaultValue = "10") size: Int,
        @RequestParam(value = "page", required = false, defaultValue = "0") page: Int,
    ): ResponseEntity<List<CampaignResponse>> {
        val (count, campaigns) = campaignService.findByAdvertiser(
            advertiserService.getById(advertiserId),
            size,
            page
        )

        return ResponseEntity(
            campaigns.map { it.toResponse() },
            HttpHeaders().apply {
                set("X-Total-Count", count.toString())
            },
            HttpStatus.OK
        )
    }

    @GetMapping("/{id}")
    fun getById(
        @PathVariable advertiserId: UUID,
        @PathVariable id: UUID
    ): CampaignResponse {
        val campaign = campaignService.getById(id)

        if (campaign.advertiser.id != advertiserId) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND)
        }

        return campaign.toResponse()
    }

    @PutMapping("/{id}")
    fun updateById(
        @PathVariable advertiserId: UUID,
        @PathVariable id: UUID,
        @Valid @RequestBody request: CampaignUpdateRequest
    ): CampaignResponse {
        val campaign = campaignService.getById(id)

        if (campaign.advertiser.id != advertiserId) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND)
        }

        return campaignService.update(
            campaign.apply {
                costPerImpression = request.costPerImpression ?: costPerImpression
                costPerClick = request.costPerClick ?: costPerClick
                adTitle = request.adTitle ?: adTitle
                adText = request.adText ?: adText
                targeting = request.targeting ?: targeting
            }
        ).toResponse()
    }

    @DeleteMapping("/{id}")
    fun deleteById(
        @PathVariable advertiserId: UUID,
        @PathVariable id: UUID
    ) {
        val campaign = campaignService.getById(id)

        if (campaign.advertiser.id != advertiserId) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND)
        }

        campaignService.delete(campaign)
    }
}