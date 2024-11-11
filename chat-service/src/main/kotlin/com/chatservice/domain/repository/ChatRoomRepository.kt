package com.chatservice.domain.repository

import com.chatservice.domain.dto.ChatRoomDTO
import com.chatservice.domain.service.RedisSubscriber
import org.springframework.data.redis.core.HashOperations
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.listener.ChannelTopic
import org.springframework.data.redis.listener.RedisMessageListenerContainer
import org.springframework.stereotype.Repository

@Repository
class ChatRoomRepository(
    private val redisMessageListenerContainer: RedisMessageListenerContainer,
    private val redisSubscriber: RedisSubscriber,
    private val redisTemplate: RedisTemplate<String, Any>
) {
    private val CHAT_ROOMS: String = "CHAT_ROOM"
    private val chatRoomMap: MutableMap<String, ChatRoomDTO> = mutableMapOf()
    private val opsHashChatRoom: HashOperations<String, String, ChatRoomDTO> = redisTemplate.opsForHash()
    private val topics: MutableMap<String, ChannelTopic> = hashMapOf()

    fun findAllRoom(): MutableList<ChatRoomDTO> {
        return opsHashChatRoom.values(CHAT_ROOMS)
    }

    fun findRoomById(roomId: String):ChatRoomDTO? {
        val chatRoomDTO = opsHashChatRoom[CHAT_ROOMS, roomId]
        return chatRoomDTO
    }

    fun createChatRoom(name: String): ChatRoomDTO {
        val room: ChatRoomDTO = ChatRoomDTO.create(name)
        opsHashChatRoom.put(CHAT_ROOMS, room.roomId, room)
        return room
    }

    fun enterChatRoom(roomId: String) {
        var channelTopic = topics[roomId]

        if (channelTopic == null) {
            channelTopic = ChannelTopic(roomId)
            redisMessageListenerContainer.addMessageListener(redisSubscriber, channelTopic)
            topics[roomId] = channelTopic
        }
    }

    fun getTopic(roomId: String): ChannelTopic {
        return topics[roomId]!!
    }
}