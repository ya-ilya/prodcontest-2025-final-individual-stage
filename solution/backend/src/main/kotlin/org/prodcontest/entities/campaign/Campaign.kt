package org.prodcontest.entities.campaign

import jakarta.persistence.*
import org.prodcontest.entities.advertiser.Advertiser
import org.prodcontest.entities.click.Click
import org.prodcontest.entities.click.nonunique.NonUniqueClick
import org.prodcontest.entities.impression.Impression
import org.prodcontest.entities.impression.nonunique.NonUniqueImpression
import org.prodcontest.responses.AdResponse
import org.prodcontest.responses.CampaignResponse
import org.prodcontest.responses.DailyStatsResponse
import org.prodcontest.responses.StatsResponse
import java.util.*

@Entity
class Campaign(
    val impressionsLimit: Int,
    val clicksLimit: Int,
    var costPerImpression: Float,
    var costPerClick: Float,
    var adTitle: String,
    var adText: String,
    var adImage: String? = null,
    val startDate: Int,
    val endDate: Int,
    @Embedded
    var targeting: CampaignTargeting = CampaignTargeting(),
    @ManyToOne
    val advertiser: Advertiser,
    @OneToMany(mappedBy = "campaign")
    val impressions: List<Impression> = emptyList(),
    @OneToMany(mappedBy = "campaign")
    val clicks: List<Click> = emptyList(),
    @OneToMany(mappedBy = "campaign")
    val nonUniqueImpressions: List<NonUniqueImpression> = emptyList(),
    @OneToMany(mappedBy = "campaign")
    val nonUniqueClicks: List<NonUniqueClick> = emptyList(),
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null
) {
    fun toResponse() = CampaignResponse(
        id!!,
        advertiser.id,
        impressionsLimit,
        clicksLimit,
        costPerImpression,
        costPerClick,
        adTitle,
        adText,
        adImage != null,
        startDate,
        endDate,
        targeting
    )

    // TODO: SQL query
    fun toStatsResponse(): StatsResponse {
        val impressions = impressions.size
        val clicks = clicks.size
        val spentImpressions = this.impressions.sumOf { it.cost.toDouble() }.toFloat()
        val spentClicks = this.clicks.sumOf { it.cost.toDouble() }.toFloat()

        return StatsResponse(
            impressions,
            clicks,
            (clicks.toFloat() / impressions.toFloat()) * 100,
            spentImpressions,
            spentClicks,
            spentClicks + spentImpressions
        )
    }

    // TODO: SQL query
    fun toDailyStatsResponse(currentDate: Int): DailyStatsResponse {
        val dailyImpressionsList = impressions.filter { it.date == currentDate }
        val dailyClicksList = clicks.filter { it.date == currentDate }
        val impressions = dailyImpressionsList.size
        val clicks = dailyClicksList.size
        val spentImpressions = dailyImpressionsList.sumOf { it.cost.toDouble() }.toFloat()
        val spentClicks = dailyClicksList.sumOf { it.cost.toDouble() }.toFloat()

        return DailyStatsResponse(
            impressions,
            clicks,
            (clicks.toFloat() / impressions.toFloat()) * 100,
            spentImpressions,
            spentClicks,
            spentClicks + spentImpressions,
            currentDate
        )
    }

    fun toAdResponse() = AdResponse(
        id!!,
        adTitle,
        adText,
        adImage != null,
        advertiser.id
    )
}