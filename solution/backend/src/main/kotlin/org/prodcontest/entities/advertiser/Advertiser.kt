package org.prodcontest.entities.advertiser

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import org.prodcontest.responses.AdvertiserResponse
import java.util.*

@Entity
class Advertiser(
    val name: String,
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null
) {
    fun toResponse() = AdvertiserResponse(
        id!!,
        name
    )
}