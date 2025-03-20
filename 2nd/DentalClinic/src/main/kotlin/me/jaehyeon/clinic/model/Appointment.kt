package me.jaehyeon.clinic.model

import java.time.LocalDateTime

data class Appointment(
    val id: String,
    val patient: Patient,
    val dentist: Dentist,
    val time: LocalDateTime,
    val treatment: Treatment,
)
