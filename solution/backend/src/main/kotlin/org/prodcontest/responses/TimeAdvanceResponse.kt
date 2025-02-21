package org.prodcontest.responses

import com.fasterxml.jackson.annotation.JsonProperty

class TimeAdvanceResponse(
    @JsonProperty("current_date")
    val currentDate: Int
)