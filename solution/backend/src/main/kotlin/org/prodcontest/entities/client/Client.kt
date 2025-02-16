package org.prodcontest.entities.client

import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
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
    val id: UUID
) {
    fun toResponse() = ClientResponse(
        id,
        login,
        age,
        location,
        gender
    )
}