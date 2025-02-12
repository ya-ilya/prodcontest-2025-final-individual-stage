package org.prodcontest

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["org.prodcontest"])
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
