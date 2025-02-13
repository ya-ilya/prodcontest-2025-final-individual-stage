package org.prodcontest.requests

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.Size
import org.prodcontest.entities.campaign.CampaignTargeting

class CampaignUpdateRequest(
    @JsonProperty("cost_per_impression")
    @Min(0)
    val costPerImpression: Float? = null,
    @JsonProperty("cost_per_click")
    @Min(0)
    val costPerClick: Float? = null,
    @JsonProperty("ad_title")
    @Size(min = 2, max = 256)
    val adTitle: String? = null,
    @JsonProperty("ad_text")
    @Size(min = 2, max = 8192)
    val adText: String? = null,
    val targeting: CampaignTargeting? = null
)