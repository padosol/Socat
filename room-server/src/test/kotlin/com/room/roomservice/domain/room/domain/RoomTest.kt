package com.room.roomservice.domain.room.domain

import io.kotest.core.spec.style.BehaviorSpec
import org.assertj.core.api.Assertions
import java.time.LocalDateTime


class RoomIdGenerator(
    val id: String
) : IdGenerator {

    override fun createId(): String {
        return id
    }
}


class RoomTest : BehaviorSpec({

    Given("방 인스턴스를 만든다.") {
        val room = Room(
                roomId = "test",
                userId = "test",
                roomName = "test",
                createAt = LocalDateTime.now(),
        )

        Assertions.assertThat(room.roomId).isEqualTo("aaaa")
    }
})

