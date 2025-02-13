package org.prodcontest.entities.campaign

import org.prodcontest.enums.Gender
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface CampaignRepository : JpaRepository<Campaign, UUID> {
    @Query(
        """
            SELECT c FROM Campaign c
            WHERE (c.targeting.gender = :gender OR c.targeting.gender = "ALL")
            AND c.targeting.ageFrom <= :age
            AND c.targeting.ageTo >= :age
            AND LOWER(c.targeting.location) = LOWER(:location)
            AND (SIZE(c.impressions) < c.impressionsLimit OR SIZE(c.clicks) < c.clicksLimit)
            AND c.startDate <= :currentDate
            AND c.endDate >= :currentDate
            ORDER BY COALESCE(
                (
                    SELECT MAX(s.score) FROM c.advertiser.mlScores s
                    WHERE s.client.id = :clientId
                ),
                0
            )
        """
    )
    fun findAds(
        @Param("gender") gender: Gender,
        @Param("age") age: Int,
        @Param("location") location: String,
        @Param("clientId") clientId: UUID,
        @Param("currentDate") currentDate: Int,
    ): List<Campaign>
}