package com.room.roomservice.domain.room.document

import com.room.roomservice.domain.room.domain.Room
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Assertions.*
import java.time.LocalDateTime

class RoomDocTest: BehaviorSpec({

    Given("Room 객체를 사용해서") {
        When("RoomDoc 객체를 생성할 수 있다.") {
            val roomId = "aaaaaa-aaaaaa-aaaaaa-aaaaaa"
            val userId = "test"
            val roomName = "테스트 룸"
            val now = LocalDateTime.now()

            val room = Room(
                    roomId = roomId,
                    userId = userId,
                    roomName = roomName,
                    createdAt = now
            )

            val roomDoc = RoomDoc(
                    roomId = room.roomId,
                    userId = room.userId,
                    roomName = room.roomName,
                    createdAt = room.createdAt
            )

            Then("Room 객체의 roomId 와 RoomDoc 객체의 roomId 는 일치한다.") {
                roomDoc.roomId shouldBe room.roomId
            }
        }

    }

})