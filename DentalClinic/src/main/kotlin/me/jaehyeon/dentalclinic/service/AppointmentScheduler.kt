package me.jaehyeon.dentalclinic.service

import me.jaehyeon.dentalclinic.api.dto.AppointmentRequest
import me.jaehyeon.dentalclinic.api.dto.AppointmentResponse
import java.util.UUID

interface AppointmentScheduler {
    fun schedule(appointmentRequest: AppointmentRequest): AppointmentResponse =
        AppointmentResponse(id = UUID.randomUUID().toString(), message = successMessage())

    fun successMessage(): String
}
