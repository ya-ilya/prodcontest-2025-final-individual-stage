package org.prodcontest.entities.mlscore

import org.prodcontest.entities.advertiser.Advertiser
import org.prodcontest.entities.client.Client
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface MLScoreRepository : JpaRepository<MLScore, UUID> {
    fun findByAdvertiserAndClient(advertiser: Advertiser, client: Client): Optional<MLScore>

}