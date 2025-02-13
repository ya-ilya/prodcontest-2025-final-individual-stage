package org.prodcontest.services

import org.prodcontest.entities.campaign.Campaign
import org.prodcontest.entities.client.Client
import org.prodcontest.entities.impression.Impression
import org.prodcontest.entities.impression.ImpressionRepository
import org.springframework.stereotype.Service

@Service
class ImpressionService(
    private val impressionRepository: ImpressionRepository,
    private val dateService: DateService
) {
    fun create(campaign: Campaign, client: Client): Impression? {
        if (impressionRepository.findByCampaignAndClient(campaign, client).isPresent) {
            return null
        }

        return impressionRepository.save(
            Impression(
                dateService.getCurrentDate(),
                campaign.costPerImpression,
                campaign,
                client
            )
        )
    }
}