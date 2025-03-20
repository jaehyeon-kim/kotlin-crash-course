package me.jaehyeon.clinic

import me.jaehyeon.clinic.api.controller.AppointmentController
import me.jaehyeon.clinic.api.dto.AppointmentRequest
import me.jaehyeon.clinic.service.DefaultAppointmentScheduler
import me.jaehyeon.clinic.service.WeekendAppointmentScheduler
import java.time.LocalDateTime
import java.time.Month

fun main() {
    val schedulers = listOf(DefaultAppointmentScheduler(), WeekendAppointmentScheduler())
    val controller = AppointmentController(schedulers)
    val request = AppointmentRequest("P1", "D1", LocalDateTime.now(), "T1")

    println(
        controller.scheduleAppointment(
            AppointmentRequest(
                "P1",
                "D1",
                LocalDateTime.of(2025, Month.MARCH, 1, 1, 1),
                "T1",
            ),
        ),
    )
    println(
        controller.scheduleAppointment(
            AppointmentRequest(
                "P1",
                "D1",
                LocalDateTime.of(2025, Month.MARCH, 3, 1, 1),
                "T1",
            ),
        ),
    )
}
