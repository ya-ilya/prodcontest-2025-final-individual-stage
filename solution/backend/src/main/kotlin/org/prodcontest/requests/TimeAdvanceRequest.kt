package org.prodcontest.requests

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.Min

class TimeAdvanceRequest(
    @JsonProperty("current_date")
    @Min(0)
    val currentDate: Int
)