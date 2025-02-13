package org.prodcontest.entities.mlscore

import jakarta.persistence.*
import org.prodcontest.entities.advertiser.Advertiser
import org.prodcontest.entities.client.Client
import java.util.*

@Entity
class MLScore(
    val score: Int,
    @ManyToOne(fetch = FetchType.EAGER)
    val advertiser: Advertiser,
    @ManyToOne(fetch = FetchType.EAGER)
    val client: Client,
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null
)