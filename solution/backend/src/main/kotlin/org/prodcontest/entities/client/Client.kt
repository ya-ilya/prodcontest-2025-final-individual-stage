package org.prodcontest.entities.client

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import org.prodcontest.enums.Gender
import java.util.*

@Entity
class Client(
    val login: String,
    val age: Int,
    val location: String,
    val gender: Gender,
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null
)