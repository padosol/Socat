package com.room.roomservice.room.domain

import com.room.roomservice.domain.room.domain.IdGenerator
import com.room.roomservice.domain.room.domain.Room
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.util.*

class RoomTest {


    @Test
    @DisplayName("방 ID 생성 테스트")
    fun roomIdCreateTest() {
        // given
        val name = "테스트 방"
        val room = Room(roomName = name)
        val roomIdGenerator = object : IdGenerator {
            var id: String? = null
            override fun createId(): String {
                id = UUID.randomUUID().toString()
                return id as String
            }
        }

        // when
        room.createRoom(roomIdGenerator)

        //then
        Assertions.assertThat(roomIdGenerator.id).isEqualTo(room.roomId)
        Assertions.assertThat(name).isEqualTo(room.roomName)
    }


}