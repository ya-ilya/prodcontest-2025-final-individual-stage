package org.prodcontest.entities.impression

import org.prodcontest.entities.campaign.Campaign
import org.prodcontest.entities.client.Client
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ImpressionRepository : JpaRepository<Impression, UUID> {
    fun findByCampaignAndClient(campaign: Campaign, client: Client): Optional<Impression>
}