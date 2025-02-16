package org.prodcontest.entities.advertiser

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import org.prodcontest.entities.campaign.Campaign
import org.prodcontest.entities.mlscore.MLScore
import org.prodcontest.responses.AdvertiserResponse
import java.util.*

@Entity
class Advertiser(
    val name: String,
    @OneToMany(mappedBy = "advertiser")
    val campaigns: List<Campaign> = listOf(),
    @OneToMany(mappedBy = "advertiser")
    val mlScores: List<MLScore> = listOf(),
    @Id
    val id: UUID
) {
    fun toResponse() = AdvertiserResponse(
        id,
        name
    )
}