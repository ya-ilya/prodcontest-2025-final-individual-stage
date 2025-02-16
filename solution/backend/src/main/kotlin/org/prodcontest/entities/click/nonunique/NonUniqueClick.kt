package org.prodcontest.entities.click.nonunique

import jakarta.persistence.*
import org.prodcontest.entities.campaign.Campaign
import org.prodcontest.entities.client.Client
import java.util.*

@Entity
class NonUniqueClick(
    var count: Int,
    @ManyToOne
    val campaign: Campaign,
    @ManyToOne
    val client: Client,
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null
)