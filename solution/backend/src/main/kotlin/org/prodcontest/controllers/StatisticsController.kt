package org.prodcontest.controllers

import org.prodcontest.responses.DailyStatsResponse
import org.prodcontest.responses.StatsResponse
import org.prodcontest.services.AdvertiserService
import org.prodcontest.services.CampaignService
import org.prodcontest.services.DateService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/stats")
class StatisticsController(
    private val campaignService: CampaignService,
    private val advertiserService: AdvertiserService,
    private val dateService: DateService
) {
    @GetMapping("/campaigns/{campaignId}")
    fun campaign(@PathVariable campaignId: UUID): StatsResponse {
        return campaignService.getById(campaignId).toStatsResponse()
    }

    @GetMapping("/campaigns/{campaignId}/daily")
    fun campaignDaily(@PathVariable campaignId: UUID): DailyStatsResponse {
        return campaignService.getById(campaignId).toDailyStatsResponse(dateService.getCurrentDate())
    }

    @GetMapping("/advertisers/{advertiserId}")
    fun advertiser(@PathVariable advertiserId: UUID): List<StatsResponse> {
        return advertiserService.getById(advertiserId).campaigns.map {
            it.toStatsResponse()
        }
    }

    @GetMapping("/advertisers/{advertiserId}/daily")
    fun advertiserDaily(@PathVariable advertiserId: UUID): List<DailyStatsResponse> {
        return advertiserService.getById(advertiserId).campaigns.map {
            it.toDailyStatsResponse(dateService.getCurrentDate())
        }
    }
}