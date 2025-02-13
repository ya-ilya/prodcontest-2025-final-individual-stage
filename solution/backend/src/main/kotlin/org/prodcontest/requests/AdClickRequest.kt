package org.prodcontest.requests

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*

class AdClickRequest(
    @JsonProperty("client_id")
    val clientId: UUID
)