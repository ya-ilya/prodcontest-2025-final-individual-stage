package org.prodcontest.responses

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*

class AdvertiserResponse(
    @JsonProperty("advertiser_id")
    val advertiserId: UUID,
    val name: String
)