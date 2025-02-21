package org.prodcontest.entities.campaign

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.Embeddable
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.Size
import org.prodcontest.enums.Gender

@Embeddable
class CampaignTargeting(
    @Enumerated(EnumType.STRING)
    val gender: Gender? = null,
    @JsonProperty("age_from")
    @Min(0)
    @Max(100)
    val ageFrom: Int? = null,
    @JsonProperty("age_to")
    @Min(0)
    @Max(100)
    val ageTo: Int? = null,
    @Size(min = 1)
    val location: String? = null
)