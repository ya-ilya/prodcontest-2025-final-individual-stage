package org.prodcontest.services

import org.prodcontest.entities.badword.BadWord
import org.prodcontest.entities.badword.BadWordRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.util.*

@Service
class TextModerationService(private val badWordRepository: BadWordRepository) {
    private val enabled = System.getenv("TEXT_MODERATION").toBooleanStrict()

    fun moderate(text: String): Boolean {
        if (!enabled) return true

        for (word in getBadWords()) {
            if (text.contains(word, true)) {
                return false
            }
        }

        return true
    }

    fun getBadWords(): List<String> {
        return badWordRepository.findAll().map { it.word }
    }

    fun findBadWordByWord(word: String): Optional<BadWord> {
        return badWordRepository.findByWord(word.lowercase())
    }

    fun isBadWordExists(word: String): Boolean {
        return findBadWordByWord(word).isPresent
    }

    fun createBadWord(word: String): BadWord {
        if (isBadWordExists(word)) {
            throw ResponseStatusException(HttpStatus.CONFLICT)
        }

        return badWordRepository.save(
            BadWord(
                word
            )
        )
    }

    fun deleteBadWord(word: String) {
        badWordRepository.delete(findBadWordByWord(word).orElseThrow {
            ResponseStatusException(HttpStatus.NOT_FOUND)
        })
    }
}