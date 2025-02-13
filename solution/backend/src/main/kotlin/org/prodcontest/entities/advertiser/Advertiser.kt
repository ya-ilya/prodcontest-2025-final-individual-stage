package org.prodcontest.entities.advertiser

import jakarta.persistence.*
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
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null
) {
    fun toResponse() = AdvertiserResponse(
        id!!,
        name
    )
}