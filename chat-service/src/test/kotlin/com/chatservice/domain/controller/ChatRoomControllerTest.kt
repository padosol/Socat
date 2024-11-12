package com.chatservice.domain.controller

import io.kotest.core.spec.style.DescribeSpec
import org.junit.jupiter.api.Assertions.*
import org.springframework.test.web.servlet.MockMvc

class ChatRoomControllerTest: DescribeSpec({
    lateinit var mockMvc: MockMvc
})