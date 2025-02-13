package org.prodcontest.services

import org.prodcontest.entities.advertiser.Advertiser
import org.prodcontest.entities.campaign.Campaign
import org.prodcontest.entities.campaign.CampaignRepository
import org.prodcontest.entities.campaign.CampaignTargeting
import org.prodcontest.entities.client.Client
import org.prodcontest.services.pagination.OffsetBasedPageRequest
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

    fun findAd(
        client: Client
    ): List<Campaign> {
        return campaignRepository.findAds(
            client.gender,
            client.age,
            client.location,
            client.id!!,
            dateService.getCurrentDate()
        )
    }

    fun findByAdvertiser(
        advertiser: Advertiser,
        size: Int,
        page: Int
    ): Pair<Int, List<Campaign>> {
        val result = campaignRepository.findAll(OffsetBasedPageRequest(offset = page, limit = size))
        return result.totalElements.toInt() to result.content
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
                startDate,
                endDate,
                targeting,
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