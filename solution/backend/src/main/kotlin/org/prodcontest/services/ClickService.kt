package org.prodcontest.services

import org.prodcontest.entities.campaign.Campaign
import org.prodcontest.entities.click.Click
import org.prodcontest.entities.click.ClickRepository
import org.prodcontest.entities.click.nonunique.NonUniqueClick
import org.prodcontest.entities.click.nonunique.NonUniqueClickRepository
import org.prodcontest.entities.client.Client
import org.springframework.stereotype.Service

@Service
class ClickService(
    private val clickRepository: ClickRepository,
    private val nonUniqueClickRepository: NonUniqueClickRepository,
    private val dateService: DateService
) {
    fun create(campaign: Campaign, client: Client): Click? {
        if (clickRepository.findByCampaignAndClient(campaign, client).isPresent) {
            val nonUniqueClick = nonUniqueClickRepository.findByCampaignAndClient(campaign, client).orElse(
                NonUniqueClick(
                    0,
                    campaign,
                    client
                )
            )

            nonUniqueClickRepository.save(
                nonUniqueClick.apply {
                    this.count += 1
                }
            )

            return null
        } else {
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
}