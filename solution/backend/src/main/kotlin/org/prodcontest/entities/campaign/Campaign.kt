package org.prodcontest.entities.campaign

import jakarta.persistence.*
import org.prodcontest.entities.advertiser.Advertiser
import org.prodcontest.responses.CampaignResponse
import java.util.*

@Entity
class Campaign(
    val impressionsLimit: Int,
    val clicksLimit: Int,
    var costPerImpression: Float,
    var costPerClick: Float,
    var adTitle: String,
    var adText: String,
    val startDate: Int,
    val endDate: Int,
    @Embedded
    var targeting: CampaignTargeting? = null,
    @ManyToOne(fetch = FetchType.EAGER)
    val advertiser: Advertiser,
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null
) {
    fun toResponse() = CampaignResponse(
        id!!,
        advertiser.id!!,
        impressionsLimit,
        clicksLimit,
        costPerImpression,
        costPerClick,
        adTitle,
        adText,
        startDate,
        endDate,
        targeting
    )
}