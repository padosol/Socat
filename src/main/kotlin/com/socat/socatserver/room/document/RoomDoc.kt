package com.socat.socatserver.room.document

import com.socat.socatserver.room.domain.Room
import nonapi.io.github.classgraph.json.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collation = "room")
class RoomDoc(
    @Id
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