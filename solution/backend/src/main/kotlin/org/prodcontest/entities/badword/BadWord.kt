package org.prodcontest.entities.badword

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.util.*

@Entity
class BadWord(
    val word: String,
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null
)