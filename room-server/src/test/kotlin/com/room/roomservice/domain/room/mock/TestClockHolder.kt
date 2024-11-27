package com.room.roomservice.domain.room.mock

import com.room.roomservice.domain.room.domain.ClockHolder

class TestClockHolder(
        private val millis: Long
) : ClockHolder {
    override fun millis(): Long {
        return millis
    }
}