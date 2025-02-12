package org.prodcontest.responses

import com.fasterxml.jackson.annotation.JsonProperty
import org.prodcontest.enums.Gender
import java.util.*

class ClientResponse(
    @JsonProperty("client_id")
    val clientId: UUID,
    val login: String,
    val age: Int,
    val location: String,
    val gender: Gender
)