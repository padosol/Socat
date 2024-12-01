package com.room.roomservice.domain.room.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.room.roomservice.domain.room.domain.Room
import com.room.roomservice.domain.room.dto.request.ModifyRoomDTO
import com.room.roomservice.domain.room.dto.request.RemoveRoomDTO
import com.room.roomservice.domain.room.dto.response.RoomResponse
import com.room.roomservice.domain.room.service.usecase.CreateRoomUserCase
import com.room.roomservice.domain.room.service.usecase.FindRoomUseCase
import com.room.roomservice.domain.room.service.usecase.ModifyRoomUseCase
import com.room.roomservice.domain.room.service.usecase.RemoveRoomUseCase
import com.room.roomservice.global.jwt.JwtProvider
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.*
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import java.time.LocalDateTime
import java.time.LocalDateTime.*
import jakarta.servlet.http.HttpServletRequest as HttpServletRequest1


@WebMvcTest(RoomController::class)
class RoomControllerTest: DescribeSpec({
    val findRoomUseCase = mockk<FindRoomUseCase>()
    val createRoomUseCase = mockk<CreateRoomUserCase>()
    val modifyRoomUseCase = mockk<ModifyRoomUseCase>()
    val removeRoomUseCase = mockk<RemoveRoomUseCase>()
    val jwtProvider = mockk<JwtProvider>()
    val roomController = RoomController(
            findRoomUseCase, createRoomUseCase, modifyRoomUseCase, removeRoomUseCase, jwtProvider
    )

    val mockMvc: MockMvc = MockMvcBuilders.standaloneSetup(roomController).build()

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
                        roomId = roomId,
                        userId = "test",
                        roomName = "테스트 룸",
                        createdAt = now
                )
                every { findRoomUseCase.findRoom(roomId) } returns room

            }.andExpect {
                MockMvcResultMatchers.status().isOk
            }
        }
    }

//    describe("PUT: /rooms" ) {
//        it("방 수정에 성공하면 200 상태코드를 반환 받는다.") {
//            val now = now()
//            mockMvc.put("/rooms") {
//                contentType = MediaType.APPLICATION_JSON
//                accept = MediaType.APPLICATION_JSON
//
//
//
//                val modifyRoomDTO = ModifyRoomDTO(
//                    roomId = "aaaaaa-aaaaaa-aaaaaa-aaaaaa",
//                    roomName = "테스트 룸",
//                    userId = "test"
//                )
//
//                val room = Room(
//                        roomId = "aaaaaa-aaaaaa-aaaaaa-aaaaaa",
//                        roomName = "테스트 룸",
//                        userId = "test",
//                        createdAt = now
//                )
//                every { modifyRoomUseCase.modify(modifyRoomDTO, "test") } returns room
//            }.andExpect {
//                status { MockMvcResultMatchers.status().isOk }
//            }
//        }
//    }

    describe("DELETE: /rooms") {
        val userId = "tester"
        val roomId = "aaaaaa-aaaaaa-aaaaaa-aaaaaa"
        val removeRoomDTO = RemoveRoomDTO(roomId)

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
            verify(exactly = 1) {jwtProvider.getUserIdByRequest(any())}
        }
    }

})


