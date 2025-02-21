package org.prodcontest.entities.campaign

import org.prodcontest.enums.Gender
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface CampaignRepository : JpaRepository<Campaign, UUID> {
    @Query(
        """
            SELECT c FROM Campaign c
            WHERE c.advertiser.id = :advertiserId
        """
    )
    fun findByAdvertiser(
        @Param("advertiserId") advertiserId: UUID,
        pageable: Pageable
    ): Page<Campaign>

    /**
     * Учитываемые показатели при фильтрации:
     * - Пол должен быть либо такой же, либо ALL
     * - Возраст должен быть больше или равен минимальному, и меньше или равен максимальному
     * - Локация должна полностью совпадать (вплоть до case)
     * - Количество показов либо количество кликов должны не превышать лимиты
     * - Текущая дата должна быть больше или равна дате начала, и меньше или равна дате окончания
     *
     * Учитываемые показатели при сортировке:
     * - ML-скор (по убыванию). Вес - 3
     * - Количество показов данному пользователю (по возрастанию). Вес - 1
     * - Количество кликов от данного пользователя (по убыванию). Вес - 2
     * - Конверсия (по убыванию). Вес - 3
     * - Количество оставшихся показов (по убыванию). Вес - 2
     * - Количество оставшихся кликов (по убыванию). Вес - 2
     *
     * @param gender Пол
     * @param age Возраст
     * @param location Локация
     * @param clientId ID клиента
     * @param currentDate Текущая дата
     * @param pageable Настройки пагинации
     */
    @Query(
        """
            SELECT c FROM Campaign c
            WHERE
                (c.targeting.gender IS NULL OR c.targeting.gender = :gender OR c.targeting.gender = 'ALL')
                AND (c.targeting.ageFrom IS NULL OR c.targeting.ageFrom <= :age)
                AND (c.targeting.ageTo IS NULL OR c.targeting.ageTo >= :age)
                AND (c.targeting.location IS NULL OR c.targeting.location = :location)
                AND (SIZE(c.impressions) <= c.impressionsLimit AND SIZE(c.clicks) <= c.clicksLimit)
                AND c.startDate <= :currentDate
                AND c.endDate >= :currentDate
            ORDER BY
                COALESCE(
                    (SELECT MAX(s.score) FROM c.advertiser.mlScores s WHERE s.client.id = :clientId),
                    (SELECT AVG(s.score) FROM c.advertiser.mlScores s WHERE 1=1)
                ) * 3 DESC,
                COALESCE((SELECT nui.count FROM c.nonUniqueImpressions nui WHERE nui.client.id = :clientId), 0) * 1 DESC,
                COALESCE((SELECT nuc.count FROM c.nonUniqueClicks nuc WHERE nuc.client.id = :clientId), 0) * 2 DESC,
                (CASE WHEN SIZE(c.impressions) > 0 THEN (SIZE(c.clicks) / SIZE(c.impressions)) ELSE 0 END) * 3 DESC,
                (c.impressionsLimit - SIZE(c.impressions)) * (CASE WHEN c.costPerImpression > c.costPerClick THEN 2 ELSE 1 END) DESC,
                (c.clicksLimit - SIZE(c.clicks)) * (CASE WHEN c.costPerClick > c.costPerImpression THEN 2 ELSE 1 END) DESC
        """
    )
    fun findRelevantAds(
        @Param("gender") gender: Gender,
        @Param("age") age: Int,
        @Param("location") location: String,
        @Param("clientId") clientId: UUID,
        @Param("currentDate") currentDate: Int,
        pageable: Pageable
    ): Page<Campaign>
}