package org.prodcontest.entities.advertiser

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.util.*

@Entity
class Advertiser(
    val name: String,
    val description: String,
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null
)