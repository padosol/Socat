package com.community.communityservice.global.exception

open class CustomException(
        open val customExceptionCode: CustomExceptionCode,
        val logMessage: String? = null
) : RuntimeException()