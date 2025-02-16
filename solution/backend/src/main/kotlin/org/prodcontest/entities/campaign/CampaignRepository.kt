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
     * - Цена за показ (по убыванию). Вес - 1
     * - Цена за клик (по убыванию). Вес - 1
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
            WHERE (c.targeting.gender = :gender OR c.targeting.gender = "ALL")
                AND c.targeting.ageFrom <= :age
                AND c.targeting.ageTo >= :age
                AND c.targeting.location = :location
                AND (SIZE(c.impressions) < c.impressionsLimit OR SIZE(c.clicks) < c.clicksLimit)
                AND c.startDate <= :currentDate
                AND c.endDate >= :currentDate
            ORDER BY
                COALESCE((SELECT MAX(s.score) FROM c.advertiser.mlScores s WHERE s.client.id = :clientId), 0) * 3 DESC,
                COALESCE((SELECT MAX(nui.count) FROM c.nonUniqueImpressions nui WHERE nui.client.id = :clientId), 0) * 1 ASC,
                COALESCE((SELECT MAX(nuc.count) FROM c.nonUniqueClicks nuc WHERE nuc.client.id = :clientId), 0) * 2 DESC,
                (SIZE(c.clicks) / SIZE(c.impressions)) * 3 DESC,
                (c.impressionsLimit - SIZE(c.impressions)) * 2 ASC,
                (c.clicksLimit - SIZE(c.clicks)) * 2 ASC,
                c.costPerImpression * 1 DESC,
                c.costPerClick * 1 DESC
        """
    )
    fun findRelevantAds(
        @Param("gender") gender: Gender,
        @Param("age") age: Int,
        @Param("location") location: String,
        @Param("clientId") clientId: UUID,
        @Param("currentDate") currentDate: Int,
        pageable: Pageable
    ): List<Campaign>
}