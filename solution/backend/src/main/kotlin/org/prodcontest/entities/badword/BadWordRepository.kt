package org.prodcontest.entities.badword

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface BadWordRepository : JpaRepository<BadWord, UUID> {
    @Query("SELECT b FROM BadWord b WHERE LOWER(b.word) = :word")
    fun findByWord(@Param("word") word: String): Optional<BadWord>
}