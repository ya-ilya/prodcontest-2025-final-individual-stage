package org.prodcontest.entities.impression.nonunique

import org.prodcontest.entities.campaign.Campaign
import org.prodcontest.entities.client.Client
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface NonUniqueImpressionRepository : JpaRepository<NonUniqueImpression, UUID> {
    fun findByCampaignAndClient(campaign: Campaign, client: Client): Optional<NonUniqueImpression>
}