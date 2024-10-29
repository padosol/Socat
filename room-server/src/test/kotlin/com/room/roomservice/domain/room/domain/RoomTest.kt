package com.room.roomservice.domain.room.domain

import io.kotest.core.listeners.AfterTestListener
import io.kotest.core.listeners.BeforeTestListener
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.junit.jupiter.api.Assertions.*
import java.util.UUID


class RoomIdGenerator : IdGenerator {
    var id: String? = null
    override fun createId(): String {
        id = UUID.randomUUID().toString()
        return id as String
    }
}


class RoomTest : BehaviorSpec({

    Given("방 인스턴스를 만든다.") {

        val room = Room(
            userId = "test",
            roomName = "testRoom"
        )

        When("방을 생성한다.") {
            val roomIdGenerator = RoomIdGenerator()
            room.createRoom(roomIdGenerator)

            Then("방 Id 가 생성된다."){
                room.roomId shouldNotBe null
                roomIdGenerator shouldNotBe null

                room.roomId.shouldBe(roomIdGenerator.id)
            }
        }
    }


})

