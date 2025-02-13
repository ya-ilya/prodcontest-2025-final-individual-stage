package org.prodcontest.entities.advertiser

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface AdvertiserRepository : JpaRepository<Advertiser, UUID>