package com.room.roomservice.domain.room.service

import com.room.roomservice.domain.room.client.UserServiceClient
import com.room.roomservice.domain.room.document.RoomDoc
import com.room.roomservice.domain.room.domain.Room
import com.room.roomservice.domain.room.dto.response.RoomResponse
import com.room.roomservice.domain.room.mock.TestRoomIdGenerator
import com.room.roomservice.domain.room.repository.RoomRepository
import com.room.roomservice.domain.room.vo.UserResponse
import io.kotest.core.spec.style.FunSpec
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.time.LocalDateTime
import java.util.*

class RoomServiceTest : FunSpec({
    
    test("방 생성 성공 테스트") {
        val useServiceClient: UserServiceClient = mockk()
        val roomRepository: RoomRepository = mockk()
        val roomService = RoomService(
                roomRepository, useServiceClient
        )

        val userResponse = UserResponse(
                userName = "tester",
                id = "test",
                email = "test@test.com"
        )

        val roomId = UUID.randomUUID().toString()
        val room = Room.create(
                userId = "test",
                roomName = "test room",
                TestRoomIdGenerator(roomId)
        )

        every { useServiceClient.getUser(any()) } returns userResponse

        val createRoom: RoomResponse = roomService.createRoom(room)

        Assertions.assertThat(createRoom.userId).isEqualTo("test")
        Assertions.assertThat(createRoom.roomName).isEqualTo("test room")
        Assertions.assertThat(createRoom.roomId).isEqualTo(roomId)
    }
    
})