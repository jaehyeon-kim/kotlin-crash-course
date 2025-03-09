package me.jaehyeon.dentalclinic.model

import java.util.Date

interface Employee {
    val role: String

    fun scheduleLeave(
        startDate: Date,
        endDate: Date,
    )
}
