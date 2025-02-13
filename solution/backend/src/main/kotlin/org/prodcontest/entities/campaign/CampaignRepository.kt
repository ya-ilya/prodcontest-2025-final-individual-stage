package org.prodcontest.entities.campaign

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface CampaignRepository : JpaRepository<Campaign, UUID>