package me.jaehyeon.clinic.api.controller

import me.jaehyeon.clinic.api.dto.AppointmentRequest
import me.jaehyeon.clinic.api.dto.AppointmentResponse
import me.jaehyeon.clinic.service.AppointmentScheduler
import me.jaehyeon.clinic.service.DefaultAppointmentScheduler
import me.jaehyeon.clinic.service.WeekendAppointmentScheduler
import java.time.DayOfWeek
import java.time.LocalDateTime

class AppointmentController(
    val appointmentSchedulers: List<AppointmentScheduler>,
) {
    fun scheduleAppointment(appointmentRequest: AppointmentRequest): AppointmentResponse {
        val appointmentScheduler = findSchedulerFor(appointmentRequest.dateTime)
        return appointmentScheduler.schedule(appointmentRequest)
    }

    private fun findSchedulerFor(date: LocalDateTime): AppointmentScheduler {
        val weekend = listOf(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY)
        return if (date.dayOfWeek in weekend) {
            appointmentSchedulers.first { it is WeekendAppointmentScheduler }
        } else {
            appointmentSchedulers.first { it is DefaultAppointmentScheduler }
        }
    }
}
