package com.room.roomservice.domain.room.dto.request

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.assertThrows

class CreateRoomDTOTest: FunSpec({

    test("roomName 이 공백이면 IllegalArgumentException 을 날린다.") {
        assertThrows<IllegalArgumentException> {
            CreateRoomDTO("")
        }
    }

    test("roomName 이 공백이 아니면 CreateRoomDTO 를 생성할 수 있다.") {
        val createRoomDTO = CreateRoomDTO("테스트 룸")
        
        createRoomDTO.roomName shouldBe "테스트 룸"
    }

})