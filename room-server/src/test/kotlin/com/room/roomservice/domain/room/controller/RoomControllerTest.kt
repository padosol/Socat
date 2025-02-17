package com.room.roomservice.domain.room.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.room.roomservice.domain.room.domain.Room
import com.room.roomservice.domain.room.dto.request.CreateRoomDTO
import com.room.roomservice.domain.room.dto.request.ModifyRoomDTO
import com.room.roomservice.domain.room.service.usecase.CreateRoomUseCase
import com.room.roomservice.domain.room.service.usecase.FindRoomUseCase
import com.room.roomservice.domain.room.service.usecase.ModifyRoomUseCase
import com.room.roomservice.domain.room.service.usecase.RemoveRoomUseCase
import com.room.roomservice.domain.room.vo.PostResponse
import com.room.roomservice.global.jwt.JwtProvider
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import java.time.LocalDateTime
import java.time.LocalDateTime.*


@WebMvcTest(RoomController::class)
class RoomControllerTest: DescribeSpec({
    val findRoomUseCase = mockk<FindRoomUseCase>()
    val createRoomUseCase = mockk<CreateRoomUseCase>()
    val modifyRoomUseCase = mockk<ModifyRoomUseCase>()
    val removeRoomUseCase = mockk<RemoveRoomUseCase>()
    val jwtProvider = mockk<JwtProvider>()
    val roomController = RoomController(
            findRoomUseCase, createRoomUseCase, modifyRoomUseCase, removeRoomUseCase, jwtProvider
    )

    val mockMvc: MockMvc = MockMvcBuilders.standaloneSetup(roomController).build()
    val objectMapper = ObjectMapper().registerKotlinModule()

    describe("/rooms") {
        it ("200 status code 를 반환한다.") {

            every { findRoomUseCase.findAllRoom() } returns emptyList()

            mockMvc.get("/rooms") {
                contentType = MediaType.APPLICATION_JSON
                accept = MediaType.APPLICATION_JSON


            }.andExpect {
                MockMvcResultMatchers.status().isOk
            }
        }
    }

    describe("/rooms/{roomId}") {
        it("roomId 를 받아 조회하면 200 코드와 RoomResponse 객체를 반환 한다.") {
            val roomId = "aaaaa-aaaaa-aaaaa-aaaaa"
            val now = now()
            mockMvc.get("/rooms/aaaaa-aaaaa-aaaaa-aaaaa") {
                contentType = MediaType.APPLICATION_JSON
                accept = MediaType.APPLICATION_JSON

                val room = Room(
                    roomId = "aaaaaa-aaaaaa-aaaaaa-aaaaaa",
                    roomName = "테스트 룸",
                    userId = "test",
                    createdAt = now,
                    roomType = "PUBLIC",
                    roomDesc = "테스트 방입니다.",
                    updatedAt = now,
                    posts = mutableListOf(),
                )
                every { findRoomUseCase.findRoomById(roomId) } returns room

            }.andExpect {
                MockMvcResultMatchers.status().isOk
            }
        }
    }

    describe("POST: /rooms 방생성 API 를 호출") {
        val roomId = "aaaaaa-aaaaaa-aaaaaa-aaaaaa"
        val roomName = "테스트 룸"
        val userId = "test"
        val roomDesc = "테스트 방입니다."
        val roomType = "PUBLIC"
        val now = now()
        val createRoomDTO = CreateRoomDTO(
                roomName = roomName,
                roomDesc = roomDesc,
                roomType = roomType,
        )
        val room = Room(
            roomId = "aaaaaa-aaaaaa-aaaaaa-aaaaaa",
            roomName = "테스트 룸",
            userId = "test",
            createdAt = now,
            roomType = roomType,
            roomDesc = roomDesc,
            updatedAt = now,
            posts = mutableListOf(),
        )
        it("CreateRoomDTO 가 유효한 값이면 201 상태코드를 반환한다.") {
            val response = mockMvc.post("/rooms") {
                contentType = MediaType.APPLICATION_JSON
                accept = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(createRoomDTO)

                every { jwtProvider.getUserIdByRequest(any()) } returns userId
                every { createRoomUseCase.createRoom(createRoomDTO, userId) } returns room
            }.andReturn().response

            response.status shouldBe 201

            // UseCase 호출 검증
            verify(exactly = 1) {createRoomUseCase.createRoom(createRoomDTO, userId)}

            // JWT 호출 검증
            verify {jwtProvider.getUserIdByRequest(any())}
        }
    }

    describe("PUT: /rooms" ) {
        val roomId = "aaaaaa-aaaaaa-aaaaaa-aaaaaa"
        val roomName = "테스트 룸"
        val userId = "test"
        val now = now()
        val modifyRoomDTO = ModifyRoomDTO(
                roomId = roomId,
                roomName = roomName,
                userId = userId
        )
        val room = Room(
            roomId = "aaaaaa-aaaaaa-aaaaaa-aaaaaa",
            roomName = "테스트 룸",
            userId = "test",
            createdAt = now,
            roomType = "PUBLIC",
            roomDesc = "테스트 방입니다.",
            updatedAt = now,
            posts = mutableListOf(),
        )

        it("방 수정에 성공하면 200 상태코드를 반환 받는다.") {
            val response = mockMvc.put("/rooms") {
                contentType = MediaType.APPLICATION_JSON
                accept = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(modifyRoomDTO)

                every { jwtProvider.getUserIdByRequest(any()) } returns userId
                every { modifyRoomUseCase.modify(modifyRoomDTO, userId) } returns room
            }.andReturn().response

            response.status shouldBe 200

            // UseCase 호출 검증
            verify(exactly = 1) {modifyRoomUseCase.modify(modifyRoomDTO, userId)}

            // JWT 호출 검증
            verify {jwtProvider.getUserIdByRequest(any())}
        }
    }

    describe("DELETE: /rooms") {
        val userId = "tester"
        val roomId = "aaaaaa-aaaaaa-aaaaaa-aaaaaa"

        it("방 삭제에 성공하면 204 상태코드를 반환 받는다.") {

            every { jwtProvider.getUserIdByRequest(any()) } returns userId
            every { removeRoomUseCase.remove(roomId, userId) } returns Unit

            val response = mockMvc.delete("/rooms") {
                contentType = MediaType.APPLICATION_JSON
                accept = MediaType.APPLICATION_JSON
                content = """
                    {
                        "roomId": "$roomId"
                    }
                """.trimIndent()
                header("Authorization", "Bearer wekrjwklerjnwlekcrjewlk")
                characterEncoding = "UTF-8"

            }.andDo { print() }
            .andReturn().response

            response.status shouldBe 204

            // UseCase 호출 검증
            verify(exactly = 1) {removeRoomUseCase.remove(roomId, userId)}
            
            // JWT 호출 검증
            verify {jwtProvider.getUserIdByRequest(any())}
        }
    }

})


