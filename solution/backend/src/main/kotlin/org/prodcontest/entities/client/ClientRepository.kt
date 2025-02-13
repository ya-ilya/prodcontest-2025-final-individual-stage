package org.prodcontest.entities.client

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ClientRepository : JpaRepository<Client, UUID>