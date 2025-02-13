package org.prodcontest.entities.click

import org.prodcontest.entities.campaign.Campaign
import org.prodcontest.entities.client.Client
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ClickRepository : JpaRepository<Click, UUID> {
    fun findByCampaignAndClient(campaign: Campaign, client: Client): Optional<Click>
}