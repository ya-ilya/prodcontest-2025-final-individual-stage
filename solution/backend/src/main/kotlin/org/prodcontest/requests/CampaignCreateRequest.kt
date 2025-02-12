package org.prodcontest.requests

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.Size
import org.prodcontest.entities.campaign.CampaignTargeting

class CampaignCreateRequest(
    @JsonProperty("impressions_limit")
    @Min(0)
    val impressionsLimit: Int,
    @JsonProperty("clicks_limit")
    @Min(0)
    val clicksLimit: Int,
    @JsonProperty("cost_per_impression")
    @Min(0)
    val costPerImpression: Float,
    @JsonProperty("cost_per_click")
    @Min(0)
    val costPerClick: Float,
    @JsonProperty("ad_title")
    @Size(min = 2, max = 256)
    val adTitle: String,
    @JsonProperty("ad_text")
    @Size(min = 2, max = 8192)
    val adText: String,
    @JsonProperty("start_date")
    @Min(0)
    val startDate: Int,
    @JsonProperty("end_date")
    @Min(0)
    val endDate: Int,
    val targeting: CampaignTargeting? = null
)