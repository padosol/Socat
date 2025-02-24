package com.community.communityservice.domain.community.service

import com.community.communityservice.domain.community.client.PostServiceClient
import com.community.communityservice.domain.community.client.UserServiceClient
import com.community.communityservice.domain.community.domain.Community
import com.community.communityservice.domain.community.domain.DefaultIdGenerator
import com.community.communityservice.domain.community.dto.request.CreateCommunityDTO
import com.community.communityservice.domain.community.dto.request.ModifyCommunityDTO
import com.community.communityservice.domain.community.exception.CommunityNoRightModifyException
import com.community.communityservice.domain.community.exception.CommunityNoRightRemoveException
import com.community.communityservice.domain.community.exception.CommunityNotFoundException
import com.community.communityservice.domain.community.repository.CommunityRepository
import com.community.communityservice.domain.community.service.usecase.CreateCommunityUseCase
import com.community.communityservice.domain.community.service.usecase.FindCommunityUseCase
import com.community.communityservice.domain.community.service.usecase.ModifyCommunityUseCase
import com.community.communityservice.domain.community.service.usecase.RemoveCommunityUseCase
import com.community.communityservice.domain.community.vo.UserResponse
import com.community.communityservice.global.dto.APIResponse
import com.community.communityservice.global.exception.CustomException
import com.community.communityservice.global.exception.CustomExceptionCode
import io.github.resilience4j.circuitbreaker.CircuitBreaker
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry
import org.springframework.stereotype.Service
import java.util.function.Supplier

@Service
class CommunityService(
    private val communityRepository: CommunityRepository,
    private val userServiceClient: UserServiceClient,
    private val postServiceClient: PostServiceClient,
    private val circuitBreakerRegistry: CircuitBreakerRegistry
) : RemoveCommunityUseCase, ModifyCommunityUseCase, FindCommunityUseCase, CreateCommunityUseCase {

    override fun createRoom(createCommunityDTO: CreateCommunityDTO, userId: String): Community {
        val userResponse = getUserResponse(userId)
        if (userResponse.data == null) {
            throw CustomException(CustomExceptionCode.USER_NOT_FOUND)
        }

        val user = userResponse.data
        val createCommunity = Community.create(
                userId = user.id,
                roomName = createCommunityDTO.roomName,
                roomDesc = createCommunityDTO.roomDesc,
                roomType = createCommunityDTO.roomType,
                DefaultIdGenerator()
        )

        return communityRepository.save(createCommunity)
    }

    override fun findRoomById(roomId: String): Community {
        return communityRepository.findById(roomId)
        ?: throw CommunityNotFoundException(CustomExceptionCode.ROOM_NOT_FOUND)
    }

    override fun findAllRoom(): List<Community> {
        return communityRepository.findAll()
    }

    override fun modify(modifyRoomDTO: ModifyCommunityDTO, userId: String): Community {
        val findCommunity: Community = communityRepository.findById(modifyRoomDTO.roomId) ?: throw RuntimeException()

        if (!findCommunity.equalsUserId(userId)) {
            throw CommunityNoRightModifyException(CustomExceptionCode.ROOM_NO_RIGHT_MODIFY)
        }

        findCommunity.modifyRoom(modifyRoomDTO)

        return communityRepository.save(findCommunity)
    }

    override fun remove(roomId: String, userId: String) {
        val room = communityRepository.findRoomByRoomIdAndUserId(roomId, userId)

        if(!room.equalsUserId(userId)) {
            throw CommunityNoRightRemoveException(CustomExceptionCode.ROOM_NO_RIGHT_DELETE)
        }

        communityRepository.delete(room.roomId)
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

