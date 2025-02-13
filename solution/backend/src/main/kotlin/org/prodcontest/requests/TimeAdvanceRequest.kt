package org.prodcontest.requests

import com.fasterxml.jackson.annotation.JsonProperty

class TimeAdvanceRequest(
    @JsonProperty("current_date")
    val currentDate: Int
)