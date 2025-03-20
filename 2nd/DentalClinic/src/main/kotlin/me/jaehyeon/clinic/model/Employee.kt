package me.jaehyeon.clinic.model

import java.util.Date

interface Employee {
    val role: String

    fun scheduleLeave(
        start: Date,
        end: Date,
    )
}
