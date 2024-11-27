package com.room.roomservice.domain.room.domain

import com.room.roomservice.domain.room.mock.TestClockHolder
import com.room.roomservice.domain.room.mock.TestRoomIdGenerator
import io.kotest.core.spec.style.FunSpec
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.util.*
import java.util.UUID.*


class RoomTest : FunSpec({

    test("create 로 룸을 생성할 수 있다.") {
        // given, when
        val roomId = randomUUID().toString()
        val room = Room.create(
                userId = "test",
                roomName = "테스트 룸",
                TestRoomIdGenerator(roomId)
        )

        Assertions.assertThat(room.roomId).isEqualTo(roomId)
        Assertions.assertThat(room.roomName).isEqualTo("테스트 룸")
        Assertions.assertThat(room.userId).isEqualTo("test")
    }

    test("modifyRoom 으로 방을 수정할 수 있다.") {
        // given
        val roomId = randomUUID().toString()
        val room = Room.create(
                userId = "test",
                roomName = "테스트 룸",
                TestRoomIdGenerator(roomId)
        )
        val updateRoom = Room(
                userId = "test",
                roomName = "테스트 룸2",
                roomId = roomId
        )

        // when
        room.modifyRoom(updateRoom)

        // then
        Assertions.assertThat(room.roomName).isEqualTo("테스트 룸2")



    }

})