package org.prodcontest.entities.campaign

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface CampaignRepository : JpaRepository<Campaign, UUID>