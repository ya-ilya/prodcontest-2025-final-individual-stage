package org.prodcontest.services

import org.prodcontest.entities.advertiser.Advertiser
import org.prodcontest.entities.campaign.Campaign
import org.prodcontest.entities.campaign.CampaignRepository
import org.prodcontest.entities.campaign.CampaignTargeting
import org.prodcontest.entities.client.Client
import org.prodcontest.services.pagination.OffsetBasedPageRequest
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.util.*

@Service
class CampaignService(
    private val campaignRepository: CampaignRepository,
    private val dateService: DateService
) {
    fun getById(id: UUID): Campaign {
        return campaignRepository
            .findById(id)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND) }
    }

    fun findRelevantAd(client: Client): Campaign {
        return campaignRepository.findRelevantAds(
            client.gender,
            client.age,
            client.location,
            client.id,
            dateService.getCurrentDate(),
            OffsetBasedPageRequest(0, 1)
        ).singleOrNull() ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
    }

    fun findByAdvertiser(
        advertiser: Advertiser,
        size: Int,
        page: Int
    ): Page<Campaign> {
        return campaignRepository.findByAdvertiser(
            advertiser.id,
            OffsetBasedPageRequest(offset = page, limit = size)
        )
    }

    fun create(
        advertiser: Advertiser,
        impressionsLimit: Int,
        clicksLimit: Int,
        costPerImpression: Float,
        costPerClick: Float,
        adTitle: String,
        adText: String,
        startDate: Int,
        endDate: Int,
        targeting: CampaignTargeting? = null
    ): Campaign {
        return campaignRepository.save(
            Campaign(
                impressionsLimit,
                clicksLimit,
                costPerImpression,
                costPerClick,
                adTitle,
                adText,
                null,
                startDate,
                endDate,
                targeting ?: CampaignTargeting(),
                advertiser
            )
        )
    }

    fun update(campaign: Campaign): Campaign {
        return campaignRepository.save(campaign)
    }

    fun delete(campaign: Campaign) {
        campaignRepository.delete(campaign)
    }
}