package com.room.roomservice.domain.room.service

import com.room.roomservice.domain.room.client.UserServiceClient
import com.room.roomservice.domain.room.document.RoomDoc
import com.room.roomservice.domain.room.domain.Room
import com.room.roomservice.domain.room.domain.RoomClockHolder
import com.room.roomservice.domain.room.domain.RoomIdGenerator
import com.room.roomservice.domain.room.dto.request.CreateRoomDTO
import com.room.roomservice.domain.room.dto.request.ModifyRoomDTO
import com.room.roomservice.domain.room.dto.response.RoomResponse
import com.room.roomservice.domain.room.exception.RoomNoRightModifyException
import com.room.roomservice.domain.room.exception.RoomNoRightRemoveException
import com.room.roomservice.domain.room.exception.RoomNotFoundException
import com.room.roomservice.domain.room.repository.RoomRepository
import com.room.roomservice.domain.room.service.usecase.CreateRoomUserCase
import com.room.roomservice.domain.room.service.usecase.FindRoomUseCase
import com.room.roomservice.domain.room.service.usecase.ModifyRoomUseCase
import com.room.roomservice.domain.room.service.usecase.RemoveRoomUseCase
import com.room.roomservice.domain.room.vo.UserResponse
import com.room.roomservice.global.exception.CustomExceptionCode
import com.room.roomservice.global.jwt.JwtProvider
import jakarta.servlet.http.HttpServletRequest
import org.springframework.stereotype.Service

@Service
class RoomService(
    private val roomRepository: RoomRepository,
    private val userServiceClient: UserServiceClient,
    private val jwtProvider: JwtProvider,
) : ModifyRoomUseCase, RemoveRoomUseCase, FindRoomUseCase, CreateRoomUserCase {

    override fun createRoom(createRoomDTO: CreateRoomDTO, request: HttpServletRequest): Room {
        val userId = jwtProvider.getUserIdByRequest(request)
        val user: UserResponse = userServiceClient.getUser(userId)

        val createRoom = Room.create(
                userId = user.id,
                roomName = createRoomDTO.roomName,
                RoomIdGenerator()
        )

        return roomRepository.save(RoomDoc.create(createRoom))
    }

    override fun findRoom(roomId: String): Room {
        return roomRepository.findById(roomId)
            ?: throw RoomNotFoundException(CustomExceptionCode.ROOM_NOT_FOUND)
    }

    override fun findAllRoom(): List<Room> {
        return roomRepository.findAll()
    }

    override fun modify(modifyRoomDTO: ModifyRoomDTO, request: HttpServletRequest): Room {
        val userId = jwtProvider.getUserIdByRequest(request)
        val findRoom: Room = roomRepository.findById(modifyRoomDTO.roomId) ?: throw RuntimeException()

        if (findRoom.userId != userId) {
            throw RoomNoRightModifyException(CustomExceptionCode.ROOM_NO_RIGHT_MODIFY)
        }

        findRoom.modifyRoom(modifyRoomDTO)

        return roomRepository.save(
                RoomDoc(
                        roomId = findRoom.roomId,
                        userId = findRoom.userId,
                        roomName = findRoom.roomName,
                        createdAt = findRoom.createdAt,
                        updatedAt = findRoom.updatedAt
                )
        )
    }

    override fun remove(roomId: String, userId: String) {
        val room = roomRepository.findRoomByRoomIdAndUserId(roomId, userId)

        if(room.userId != userId) {
            throw RoomNoRightRemoveException(CustomExceptionCode.ROOM_NO_RIGHT_DELETE)
        }

        roomRepository.delete(room.roomId)
    }

}