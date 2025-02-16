package org.prodcontest.entities.impression.nonunique

import jakarta.persistence.*
import org.prodcontest.entities.campaign.Campaign
import org.prodcontest.entities.client.Client
import java.util.*

@Entity
class NonUniqueImpression(
    var count: Int,
    @ManyToOne
    val campaign: Campaign,
    @ManyToOne
    val client: Client,
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null
)