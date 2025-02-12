package org.prodcontest.entities.campaign

import jakarta.persistence.*
import org.prodcontest.entities.advertiser.Advertiser
import java.util.*

@Entity
class Campaign(
    val impressionsLimit: Int,
    val clicksLimit: Int,
    val costPerImpression: Float,
    val costPerClick: Float,
    val adTitle: String,
    val adText: String,
    val startDate: Int,
    val endDate: Int,
    @Embedded
    val targeting: CampaignTargeting,
    @ManyToOne(fetch = FetchType.EAGER)
    val advertiser: Advertiser? = null,
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null
)