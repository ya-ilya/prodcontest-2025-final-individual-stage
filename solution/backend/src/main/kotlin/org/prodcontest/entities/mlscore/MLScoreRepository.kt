package org.prodcontest.entities.mlscore

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface MLScoreRepository : JpaRepository<MLScore, UUID>