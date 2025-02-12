package org.prodcontest.entities.advertiser

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface AdvertiserRepository : JpaRepository<Advertiser, UUID>