package com.community.communityservice.domain.community.controller

import com.community.communityservice.domain.community.dto.request.topic.CreateTopicDTO
import com.community.communityservice.domain.community.dto.request.topic.ModifyTopicDTO
import com.community.communityservice.domain.community.dto.request.topic.RemoveTopicDTO
import com.community.communityservice.domain.community.dto.response.topic.TopicResponse
import com.community.communityservice.domain.community.mapper.TopicMapper
import com.community.communityservice.domain.community.service.topic.TopicService
import com.community.communityservice.global.dto.APIResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/topics")
class TopicsController(
    private val topicService: TopicService
) {

    @PostMapping
    fun create(
        @RequestBody createTopicDTO: CreateTopicDTO
    ): ResponseEntity<APIResponse<TopicResponse>> {
        return topicService.create(createTopicDTO).let {
            ResponseEntity.ok(APIResponse.ok(TopicMapper.domainToResponse(it)))
        }
    }

    @PutMapping
    fun modify(
        @RequestBody modifyTopicDTO: ModifyTopicDTO
    ) : ResponseEntity<APIResponse<TopicResponse>> {
        return topicService.modify(modifyTopicDTO).let {
            ResponseEntity.ok(APIResponse.ok(TopicMapper.domainToResponse(it)))
        }
    }

    @DeleteMapping
    fun remove(
        @RequestBody removeTopicDTO: RemoveTopicDTO
    ) : ResponseEntity<APIResponse<Void>> {
        return topicService.remove(removeTopicDTO).let {
            ResponseEntity.status(204).body(null)
        }
    }

    @GetMapping
    fun findAll() : ResponseEntity<APIResponse<List<TopicResponse>>> {
        return topicService.findAll().map { TopicMapper.domainToResponse(it) }.let {
            ResponseEntity.ok(APIResponse.ok(it))
        }
    }

    @GetMapping("/{topicId}")
    fun findById(
        @PathVariable("topicId") topicId: String
    ) : ResponseEntity<APIResponse<TopicResponse>> {
        return topicService.findById(topicId).let {
            ResponseEntity.ok(APIResponse.ok(TopicMapper.domainToResponse(it)))
        }
    }

}