package org.prodcontest.entities.click.nonunique

import org.prodcontest.entities.campaign.Campaign
import org.prodcontest.entities.client.Client
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface NonUniqueClickRepository : JpaRepository<NonUniqueClick, UUID> {
    fun findByCampaignAndClient(campaign: Campaign, client: Client): Optional<NonUniqueClick>
}