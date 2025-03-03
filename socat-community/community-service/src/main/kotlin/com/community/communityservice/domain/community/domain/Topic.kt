package com.community.communityservice.domain.community.domain

class Topic(
    val topicId: String,
    var topicName: String
) {

    fun changeName(topicName: String) {
        this.topicName = topicName
    }

}