package me.jaehyeon.clinic.service

import me.jaehyeon.clinic.api.dto.AppointmentRequest
import me.jaehyeon.clinic.api.dto.AppointmentResponse
import java.util.UUID

interface AppointmentScheduler {
    fun schedule(appointmentRequest: AppointmentRequest): AppointmentResponse =
        AppointmentResponse(id = UUID.randomUUID().toString(), message = successMessage())

    fun successMessage(): String
}

class DefaultAppointmentScheduler : AppointmentScheduler {
    override fun successMessage() = "Scheduled on a weekday!"
}

class WeekendAppointmentScheduler : AppointmentScheduler {
    override fun successMessage() = "Scheduled on a weekend!"
}
