package com.room.roomservice.domain.room.service

import com.room.roomservice.domain.room.client.PostServiceClient
import com.room.roomservice.domain.room.client.UserServiceClient
import com.room.roomservice.domain.room.domain.Post
import com.room.roomservice.domain.room.domain.Room
import com.room.roomservice.domain.room.domain.RoomIdGenerator
import com.room.roomservice.domain.room.dto.request.CreateRoomDTO
import com.room.roomservice.domain.room.dto.request.ModifyRoomDTO
import com.room.roomservice.domain.room.exception.RoomNoRightModifyException
import com.room.roomservice.domain.room.exception.RoomNoRightRemoveException
import com.room.roomservice.domain.room.exception.RoomNotFoundException
import com.room.roomservice.domain.room.repository.RoomRepository
import com.room.roomservice.domain.room.service.usecase.CreateRoomUseCase
import com.room.roomservice.domain.room.service.usecase.FindRoomUseCase
import com.room.roomservice.domain.room.service.usecase.ModifyRoomUseCase
import com.room.roomservice.domain.room.service.usecase.RemoveRoomUseCase
import com.room.roomservice.domain.room.vo.PostResponse
import com.room.roomservice.domain.room.vo.UserResponse
import com.room.roomservice.global.dto.APIResponse
import com.room.roomservice.global.exception.CustomException
import com.room.roomservice.global.exception.CustomExceptionCode
import io.github.resilience4j.circuitbreaker.CircuitBreaker
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory
import org.springframework.stereotype.Service
import java.util.function.Supplier

@Service
class RoomService(
    private val roomRepository: RoomRepository,
    private val userServiceClient: UserServiceClient,
    private val postServiceClient: PostServiceClient,
    private val circuitBreakerRegistry: CircuitBreakerRegistry
) : RemoveRoomUseCase, ModifyRoomUseCase, FindRoomUseCase, CreateRoomUseCase {

    override fun createRoom(createRoomDTO: CreateRoomDTO, userId: String): Room {
        val userResponse = getUserResponse(userId)
        if (userResponse.data == null) {
            throw CustomException(CustomExceptionCode.USER_NOT_FOUND)
        }

        val user = userResponse.data
        val createRoom = Room.create(
                userId = user.id,
                roomName = createRoomDTO.roomName,
                roomDesc = createRoomDTO.roomDesc,
                roomType = createRoomDTO.roomType,
                RoomIdGenerator()
        )

        return roomRepository.save(createRoom)
    }

    override fun findRoomById(roomId: String): Room {
        return roomRepository.findById(roomId)
        ?: throw RoomNotFoundException(CustomExceptionCode.ROOM_NOT_FOUND)
    }

    override fun findAllRoom(): List<Room> {
        return roomRepository.findAll()
    }

    override fun modify(modifyRoomDTO: ModifyRoomDTO, userId: String): Room {
        val findRoom: Room = roomRepository.findById(modifyRoomDTO.roomId) ?: throw RuntimeException()

        if (!findRoom.equalsUserId(userId)) {
            throw RoomNoRightModifyException(CustomExceptionCode.ROOM_NO_RIGHT_MODIFY)
        }

        findRoom.modifyRoom(modifyRoomDTO)

        return roomRepository.save(findRoom)
    }

    override fun remove(roomId: String, userId: String) {
        val room = roomRepository.findRoomByRoomIdAndUserId(roomId, userId)

        if(!room.equalsUserId(userId)) {
            throw RoomNoRightRemoveException(CustomExceptionCode.ROOM_NO_RIGHT_DELETE)
        }

        roomRepository.delete(room.roomId)
    }

    fun getUserResponse(userId: String): APIResponse<UserResponse> {
        val circuitBreaker = circuitBreakerRegistry.circuitBreaker("userCircuitBreaker")
        val supplier = Supplier { userServiceClient.getUser(userId) }
        val decoratedSupplier = CircuitBreaker.decorateSupplier(circuitBreaker, supplier)

        return runCatching { decoratedSupplier.get() }
            .recover { APIResponse.fail(null) }
            .getOrThrow()
    }

}

