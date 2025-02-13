package org.prodcontest.services

import org.prodcontest.entities.campaign.Campaign
import org.prodcontest.entities.click.Click
import org.prodcontest.entities.click.ClickRepository
import org.prodcontest.entities.client.Client
import org.springframework.stereotype.Service

@Service
class ClickService(
    private val clickRepository: ClickRepository,
    private val dateService: DateService
) {
    fun create(campaign: Campaign, client: Client): Click? {
        if (clickRepository.findByCampaignAndClient(campaign, client).isPresent) {
            return null
        }

        return clickRepository.save(
            Click(
                dateService.getCurrentDate(),
                campaign.costPerClick,
                campaign,
                client
            )
        )
    }
}