package org.prodcontest.services

import org.prodcontest.entities.campaign.Campaign
import org.prodcontest.entities.client.Client
import org.prodcontest.entities.impression.Impression
import org.prodcontest.entities.impression.ImpressionRepository
import org.prodcontest.entities.impression.nonunique.NonUniqueImpression
import org.prodcontest.entities.impression.nonunique.NonUniqueImpressionRepository
import org.springframework.stereotype.Service

@Service
class ImpressionService(
    private val impressionRepository: ImpressionRepository,
    private val nonUniqueImpressionRepository: NonUniqueImpressionRepository,
    private val dateService: DateService
) {
    fun create(campaign: Campaign, client: Client): Impression? {
        if (impressionRepository.findByCampaignAndClient(campaign, client).isPresent) {
            val nonUniqueImpression = nonUniqueImpressionRepository.findByCampaignAndClient(campaign, client).orElse(
                NonUniqueImpression(
                    0,
                    campaign,
                    client
                )
            )

            nonUniqueImpressionRepository.save(
                nonUniqueImpression.apply {
                    this.count += 1
                }
            )

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