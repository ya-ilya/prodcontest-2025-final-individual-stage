package org.prodcontest.services

import org.prodcontest.entities.advertiser.Advertiser
import org.prodcontest.entities.advertiser.AdvertiserRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.util.*

@Service
class AdvertiserService(private val advertiserRepository: AdvertiserRepository) {
    fun getById(id: UUID): Advertiser {
        return advertiserRepository
            .findById(id)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND) }
    }

    fun createOrUpdate(
        id: UUID,
        name: String
    ): Advertiser {
        return advertiserRepository.save(
            Advertiser(
                name,
                id = id
            )
        )
    }
}