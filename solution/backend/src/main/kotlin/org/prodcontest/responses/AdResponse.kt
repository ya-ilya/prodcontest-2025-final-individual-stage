package org.prodcontest.responses

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*

class AdResponse(
    @JsonProperty("ad_id")
    val adId: UUID,
    @JsonProperty("ad_title")
    val adTitle: String,
    @JsonProperty("ad_text")
    val adText: String,
    @JsonProperty("advertiser_id")
    val advertiserId: UUID
)