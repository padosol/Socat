package com.socat.socatserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SocatServerApplication

fun main(args: Array<String>) {
    runApplication<SocatServerApplication>(*args)
}
