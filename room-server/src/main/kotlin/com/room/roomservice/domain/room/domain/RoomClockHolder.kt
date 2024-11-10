package com.room.roomservice.domain.room.domain

import java.time.LocalDateTime
import java.time.temporal.TemporalField

class RoomClockHolder : ClockHolder {
    override fun millis(): Long {
        return System.currentTimeMillis()
    }
}