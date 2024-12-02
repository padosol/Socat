package com.room.roomservice.domain.room.domain

import com.room.roomservice.domain.room.dto.request.ModifyRoomDTO
import com.room.roomservice.domain.room.mock.TestClockHolder
import com.room.roomservice.domain.room.mock.TestRoomIdGenerator
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.time.LocalDateTime
import java.util.*
import java.util.UUID.*


class RoomTest : BehaviorSpec({

    Given("값이 주어졌을때") {
        val roomId = randomUUID().toString()
        val roomName = "테스트 룸"
        val userId = "tester"

        When("Room.create() 함수로 Room 을 생성할 수 있다.") {
            val room = Room.create(
                    userId = userId,
                    roomName = roomName,
                    TestRoomIdGenerator(roomId)
            )

            Then("값을 비교한다.") {
                room.roomId shouldBe roomId
                room.roomName shouldBe roomName
                room.userId shouldBe userId
            }
        }
    }

    Given("modifyRoomDTO 가 주어졌을 때") {
        val modifyRoomDTO = ModifyRoomDTO(
                "test", "test room", "tester"
        )
        When("Room.modifyRoom() 함수를 사용해서 Room 을 수정할 수 있다.") {
            val room = Room(
                    "test", "tester", "tester", LocalDateTime.now()
            )
            room.modifyRoom(modifyRoomDTO)

            Then("roomName 이 변경된다.") {
                room.roomName shouldBe modifyRoomDTO.roomName
            }
        }
    }

})