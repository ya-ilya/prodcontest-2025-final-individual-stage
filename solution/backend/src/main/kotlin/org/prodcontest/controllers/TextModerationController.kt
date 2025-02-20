package org.prodcontest.controllers

import org.prodcontest.services.TextModerationService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/text-moderation")
class TextModerationController(private val textModerationService: TextModerationService) {
    @GetMapping("/bad-words")
    fun getBadWords(): List<String> {
        return textModerationService.getBadWords()
    }

    @PostMapping("/bad-words")
    fun createBadWord(@RequestParam("word") word: String) {
        if (word.split(" ", "\t").size != 1) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST)
        }

        textModerationService.createBadWord(word)
    }

    @DeleteMapping("/bad-words")
    fun deleteBadWord(@RequestParam("word") word: String) {
        textModerationService.deleteBadWord(word)
    }
}