package me.jaehyeon.clinic.api.dto

import java.time.LocalDateTime

data class AppointmentRequest(
    val patientId: String,
    val dentalPractitionerId: String,
    val dateTime: LocalDateTime,
    val treatmentId: String,
)

data class AppointmentResponse(
    val id: String? = null,
    val message: String,
)
