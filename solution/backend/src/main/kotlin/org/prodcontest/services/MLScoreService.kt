package org.prodcontest.services

import org.prodcontest.entities.advertiser.Advertiser
import org.prodcontest.entities.client.Client
import org.prodcontest.entities.mlscore.MLScore
import org.prodcontest.entities.mlscore.MLScoreRepository
import org.springframework.stereotype.Service

@Service
class MLScoreService(private val mlScoreRepository: MLScoreRepository) {
    fun createOrUpdate(
        client: Client,
        advertiser: Advertiser,
        score: Int
    ): MLScore {
        return mlScoreRepository.save(
            MLScore(
                score,
                advertiser,
                client
            )
        )
    }
}