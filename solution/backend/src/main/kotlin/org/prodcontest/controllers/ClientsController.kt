package org.prodcontest.controllers

import jakarta.validation.Valid
import org.prodcontest.requests.ClientUpsertRequest
import org.prodcontest.responses.ClientResponse
import org.prodcontest.services.ClientService
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/clients")
class ClientsController(private val clientService: ClientService) {
    @GetMapping("/{id}")
    fun getById(@PathVariable id: UUID): ClientResponse {
        return clientService.getById(id).toResponse()
    }

    @PostMapping("/bulk")
    fun bulk(@Valid @RequestBody request: List<ClientUpsertRequest>) {
        request.forEach {
            clientService.createOrUpdate(
                it.clientId,
                it.login,
                it.age,
                it.location,
                it.gender
            )
        }
    }
}