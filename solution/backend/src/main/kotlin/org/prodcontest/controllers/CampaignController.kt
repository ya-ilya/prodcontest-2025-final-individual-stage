package org.prodcontest.controllers

import jakarta.validation.Valid
import org.prodcontest.requests.CampaignCreateRequest
import org.prodcontest.requests.CampaignUpdateRequest
import org.prodcontest.responses.CampaignResponse
import org.prodcontest.services.AdvertiserService
import org.prodcontest.services.CampaignService
import org.prodcontest.services.ImageStorageService
import org.prodcontest.services.TextModerationService
import org.springframework.core.io.ByteArrayResource
import org.springframework.core.io.Resource
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.server.ResponseStatusException
import java.util.*

@RestController
@RequestMapping("/advertisers/{advertiserId}/campaigns")
class CampaignController(
    private val campaignService: CampaignService,
    private val advertiserService: AdvertiserService,
    private val imageStorageService: ImageStorageService,
    private val textModerationService: TextModerationService
) {
    @PostMapping
    fun create(
        @PathVariable advertiserId: UUID,
        @Valid @RequestBody request: CampaignCreateRequest
    ): ResponseEntity<CampaignResponse> {
        if (!textModerationService.moderate(request.adTitle) || !textModerationService.moderate(request.adText)) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST)
        }

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(
                campaignService.create(
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
            )
    }

    @GetMapping
    fun get(
        @PathVariable advertiserId: UUID,
        @RequestParam(value = "size", required = false, defaultValue = "10") size: Int,
        @RequestParam(value = "page", required = false, defaultValue = "0") page: Int,
    ): ResponseEntity<List<CampaignResponse>> {
        val result = campaignService.findByAdvertiser(
            advertiserService.getById(advertiserId),
            size,
            page
        )

        return ResponseEntity
            .status(HttpStatus.OK)
            .header("X-Total-Count", result.totalElements.toString())
            .header("X-Total-Pages", result.totalPages.toString())
            .body(result.content.map { it.toResponse() })
    }

    @GetMapping("/{id}")
    fun get(
        @PathVariable advertiserId: UUID,
        @PathVariable id: UUID
    ): CampaignResponse {
        val campaign = campaignService.getById(id)

        if (campaign.advertiser.id != advertiserId) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND)
        }

        return campaign.toResponse()
    }

    @ResponseBody
    @GetMapping("/{id}/image", produces = [MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE])
    fun getImage(
        @PathVariable advertiserId: UUID,
        @PathVariable id: UUID
    ): ResponseEntity<Resource> {
        val campaign = campaignService.getById(id)

        if (campaign.advertiser.id != advertiserId) {
            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .build()
        }

        if (campaign.adImage == null) {
            return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build()
        }

        try {
            val resource = imageStorageService.loadImage(campaign.adImage!!)

            return ResponseEntity
                .status(HttpStatus.OK)
                .body(ByteArrayResource(resource.contentAsByteArray))
        } catch (ex: RuntimeException) {
            campaignService.update(campaign.apply {
                this.adImage = null
            })

            return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build()
        }
    }

    @PostMapping("/{id}/image", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun uploadImage(
        @PathVariable advertiserId: UUID,
        @PathVariable id: UUID,
        @RequestParam("image") image: MultipartFile
    ) {
        val campaign = campaignService.getById(id)

        if (campaign.advertiser.id != advertiserId) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND)
        }

        val fileName = imageStorageService.storeImage(image)

        campaignService.update(
            campaign.apply {
                if (this.adImage != null) {
                    imageStorageService.deleteImage(this.adImage!!)
                }

                this.adImage = fileName
            }
        )
    }

    @DeleteMapping("/{id}/image")
    fun deleteImage(
        @PathVariable advertiserId: UUID,
        @PathVariable id: UUID
    ) {
        val campaign = campaignService.getById(id)

        if (campaign.advertiser.id != advertiserId) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND)
        }

        if (campaign.adImage != null) {
            imageStorageService.deleteImage(campaign.adImage!!)
        }
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