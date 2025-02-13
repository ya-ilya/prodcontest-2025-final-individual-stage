package org.prodcontest.responses

import com.fasterxml.jackson.annotation.JsonProperty

class StatsResponse(
    @JsonProperty("impressions_count")
    val impressionsCount: Int,
    @JsonProperty("clicks_count")
    val clicksCount: Int,
    @JsonProperty("conversion")
    val conversion: Float,
    @JsonProperty("spent_impressions")
    val spentImpressions: Float,
    @JsonProperty("spent_clicks")
    val spentClicks: Float,
    @JsonProperty("spent_total")
    val spentTotal: Float
)