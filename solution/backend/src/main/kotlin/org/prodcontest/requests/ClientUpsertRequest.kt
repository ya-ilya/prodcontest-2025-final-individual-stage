package org.prodcontest.requests

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.Size
import org.prodcontest.enums.Gender
import java.util.UUID

class ClientUpsertRequest(
    @JsonProperty("client_id")
    val clientId: UUID,
    @Size(min = 2, max = 256)
    val login: String,
    @Min(0)
    @Max(100)
    val age: Int,
    @Size(min = 1, max = 256)
    val location: String,
    val gender: Gender
)