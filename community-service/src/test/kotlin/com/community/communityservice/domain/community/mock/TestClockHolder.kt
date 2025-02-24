package com.community.communityservice.domain.community.mock

import com.community.communityservice.domain.community.domain.ClockHolder

class TestClockHolder(
        private val millis: Long
) : ClockHolder {
    override fun millis(): Long {
        return millis
    }
}