package com.socat.socatserver.room.document

import com.socat.socatserver.room.domain.Room
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "room")
class RoomDoc(

    @org.springframework.data.annotation.Id
    val roomId: String,
    val roomName: String,
    val createdAt: LocalDateTime
) {

    companion object {
        fun create(room: Room): RoomDoc {
            return RoomDoc(
                roomId = room.roomId!!,
                roomName = room.roomName,
                createdAt = room.createAt!!
            )
        }
    }


}