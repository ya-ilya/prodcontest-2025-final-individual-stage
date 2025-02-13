package org.prodcontest.services

import org.prodcontest.entities.client.Client
import org.prodcontest.entities.client.ClientRepository
import org.prodcontest.enums.Gender
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.util.*

@Service
class ClientService(private val clientRepository: ClientRepository) {
    fun getById(id: UUID): Client {
        return clientRepository
            .findById(id)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND) }
    }

    fun createOrUpdate(
        id: UUID,
        login: String,
        age: Int,
        location: String,
        gender: Gender
    ): Client {
        if (clientRepository.findByLogin(login).isPresent) {
            throw ResponseStatusException(HttpStatus.CONFLICT)
        }

        return clientRepository.save(
            Client(
                login,
                age,
                location,
                gender,
                id
            )
        )
    }
}