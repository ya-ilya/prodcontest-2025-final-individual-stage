package org.prodcontest.responses

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.UUID

class AdvertiserResponse(
    @JsonProperty("advertiser_id")
    val advertiserId: UUID,
    val name: String
)