package org.prodcontest.requests

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*

class MLScoreRequest(
    @JsonProperty("client_id")
    val clientId: UUID,
    @JsonProperty("advertiser_id")
    val advertiserId: UUID,
    val score: Int
)