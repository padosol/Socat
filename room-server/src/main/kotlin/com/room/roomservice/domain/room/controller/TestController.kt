package com.room.roomservice.domain.room.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController {
    private val test = "test"

    @GetMapping("/")
    fun test(): String {
        return test
    }
}