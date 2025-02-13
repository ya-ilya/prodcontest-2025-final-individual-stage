package org.prodcontest.entities.impression

import jakarta.persistence.*
import org.prodcontest.entities.campaign.Campaign
import org.prodcontest.entities.client.Client
import java.util.*

@Entity
class Impression(
    val date: Int,
    val cost: Float,
    @ManyToOne
    val campaign: Campaign,
    @ManyToOne
    val client: Client,
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null
)