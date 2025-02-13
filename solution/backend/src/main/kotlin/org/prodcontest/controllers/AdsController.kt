package org.prodcontest.controllers

import org.prodcontest.responses.AdResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/ads")
class AdsController {
    @GetMapping
    fun get(@RequestParam(required = true, name = "client_id") clientId: UUID): AdResponse {
        TODO()
    }
}