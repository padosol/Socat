package com.community.communityservice.global.dto

import com.community.communityservice.global.exception.ErrorResponse

class APIResponse<T>(
        val success: Boolean,
        val data: T?,
        val error: ErrorResponse?,
) {

    companion object {
        fun <T> ok(data: T): APIResponse<T> {
            return APIResponse(true, data, null)
        }

        fun <T> fail(error: ErrorResponse?): APIResponse<T> {
            return APIResponse(false, null, error)
        }
    }
}