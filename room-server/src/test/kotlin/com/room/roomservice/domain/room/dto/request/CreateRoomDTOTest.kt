package com.room.roomservice.domain.room.dto.request

import io.kotest.core.spec.style.FunSpec
import org.junit.jupiter.api.assertThrows

class CreateRoomDTOTest: FunSpec({

    test("roomName 이 공백이면 IllegalArgumentException 을 날린다.") {
        assertThrows<IllegalArgumentException> {
            CreateRoomDTO("")
        }
    }

})