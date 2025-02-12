package org.prodcontest.requests

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.Size
import java.util.UUID

class AdvertiserUpsertRequest(
    @JsonProperty("advertiser_id")
    val advertiserId: UUID,
    @Size(min = 2, max = 256)
    val name: String
)