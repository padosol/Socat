package com.community.communityservice.domain.community.domain

class DefaultClockHolder : ClockHolder {
    override fun millis(): Long {
        return System.currentTimeMillis()
    }
}