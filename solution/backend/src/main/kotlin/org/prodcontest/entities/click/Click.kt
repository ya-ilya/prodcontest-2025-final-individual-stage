package org.prodcontest.entities.click

import jakarta.persistence.*
import org.prodcontest.entities.campaign.Campaign
import org.prodcontest.entities.client.Client
import java.util.*

@Entity
class Click(
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