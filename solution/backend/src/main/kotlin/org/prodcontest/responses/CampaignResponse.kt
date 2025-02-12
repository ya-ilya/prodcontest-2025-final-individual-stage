package org.prodcontest.responses

import com.fasterxml.jackson.annotation.JsonProperty
import org.prodcontest.entities.campaign.CampaignTargeting
import java.util.*

class CampaignResponse(
    @JsonProperty("campaign_id")
    val campaignId: UUID,
    @JsonProperty("advertiser_id")
    val advertiserId: UUID,
    @JsonProperty("impressions_limit")
    val impressionsLimit: Int,
    @JsonProperty("clicks_limit")
    val clicksLimit: Int,
    @JsonProperty("cost_per_impression")
    val costPerImpression: Float,
    @JsonProperty("cost_per_click")
    val costPerClick: Float,
    @JsonProperty("ad_title")
    val adTitle: String,
    @JsonProperty("ad_text")
    val adText: String,
    @JsonProperty("start_date")
    val startDate: Int,
    @JsonProperty("end_date")
    val endDate: Int,
    val targeting: CampaignTargeting? = null
)