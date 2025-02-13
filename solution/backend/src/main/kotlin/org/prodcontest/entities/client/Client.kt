package org.prodcontest.entities.client

import jakarta.persistence.*
import org.prodcontest.enums.Gender
import org.prodcontest.responses.ClientResponse
import java.util.*

@Entity
class Client(
    val login: String,
    val age: Int,
    val location: String,
    @Enumerated(EnumType.STRING)
    val gender: Gender,
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null
) {
    fun toResponse() = ClientResponse(
        id!!,
        login,
        age,
        location,
        gender
    )
}