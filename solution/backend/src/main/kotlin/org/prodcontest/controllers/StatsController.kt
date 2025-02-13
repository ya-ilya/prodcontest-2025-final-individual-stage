package org.prodcontest.controllers

import org.prodcontest.responses.StatsResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/stats")
class StatsController {
    @GetMapping("/campaigns/{campaignId}")
    fun campaign(@PathVariable campaignId: UUID): StatsResponse {
        TODO()
    }

    @GetMapping("/campaigns/{campaignId}/daily")
    fun campaignDaily(@PathVariable campaignId: UUID): StatsResponse {
        TODO()
    }

    @GetMapping("/advertisers/{advertiserId}")
    fun advertiser(@PathVariable advertiserId: UUID): StatsResponse {
        TODO()
    }

    @GetMapping("/advertisers/{advertiserId}/daily")
    fun advertiserDaily(@PathVariable advertiserId: UUID): StatsResponse {
        TODO()
    }
}