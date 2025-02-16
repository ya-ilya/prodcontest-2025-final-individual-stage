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
        val mlScore = mlScoreRepository.findByAdvertiserAndClient(advertiser, client).orElseGet {
            MLScore(
                score,
                advertiser,
                client
            )
        }

        return mlScoreRepository.save(
            mlScore.apply {
                this.score = score
            }
        )
    }
}