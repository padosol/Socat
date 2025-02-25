package com.community.communityservice.domain.community.service.topic

import com.community.communityservice.domain.community.domain.Topic
import com.community.communityservice.domain.community.dto.request.topic.CreateTopicDTO
import com.community.communityservice.domain.community.dto.request.topic.ModifyTopicDTO
import com.community.communityservice.domain.community.repository.topic.TopicRepository
import com.community.communityservice.domain.community.service.topic.usecase.CreateTopicUseCase
import com.community.communityservice.domain.community.service.topic.usecase.FIndTopicUseCase
import com.community.communityservice.domain.community.service.topic.usecase.ModifyTopicUseCase
import com.community.communityservice.domain.community.service.topic.usecase.RemoveTopicUseCase
import com.community.communityservice.global.exception.CustomException
import com.community.communityservice.global.exception.CustomExceptionCode
import lombok.extern.slf4j.Slf4j
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service


@Slf4j
@Service
class TopicService(
    private val topicRepository: TopicRepository
) : CreateTopicUseCase, ModifyTopicUseCase, RemoveTopicUseCase, FIndTopicUseCase{

    private val logger: Logger = LoggerFactory.getLogger(TopicService::class.java)

    override fun create(createTopicDTO: CreateTopicDTO): Topic {
        topicRepository.findById(createTopicDTO.topicId) ?.let {
            logger.info("이미 등록된 토픽 아이디 입니다. [${createTopicDTO.topicId}]")
            throw CustomException(CustomExceptionCode.TOPIC_ID_ALREADY_EXISTS)
        }

        val topic = Topic(
            topicId = createTopicDTO.topicId,
            topicName = createTopicDTO.topicName
        )

        return topicRepository.save(topic)
    }

    override fun findById(topicId: String): Topic {
        return topicRepository.findById(topicId)
            ?: run {
                logger.info("존재하지 않는 토픽 아이디 [${topicId}]")
                throw CustomException(CustomExceptionCode.TOPIC_ID_NOT_EXISTS)
            }
    }

    override fun findAll(): List<Topic> {
        return topicRepository.findAll()
    }

    override fun modify(topicId: String, modifyTopicDTO: ModifyTopicDTO): Topic {
        val topic = topicRepository.findById(topicId) ?: throw CustomException(CustomExceptionCode.TOPIC_ID_NOT_EXISTS)

        topic.changeName(modifyTopicDTO.topicName)

        return topicRepository.save(topic)
    }

    override fun remove(topicId: String) {
        topicRepository.findById(topicId) ?: {
            logger.info("존재하지 않는 토픽 아이디 [${topicId}]")
            throw CustomException(CustomExceptionCode.TOPIC_ID_NOT_EXISTS)
        }

        topicRepository.delete(topicId)
    }
}