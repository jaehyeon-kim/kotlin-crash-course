package me.jaehyeon.dentalclinic.api.controller

import jakarta.validation.Valid
import me.jaehyeon.dentalclinic.api.dto.AppointmentDetailDTO
import me.jaehyeon.dentalclinic.api.dto.AppointmentRequest
import me.jaehyeon.dentalclinic.api.dto.AppointmentResponse
import me.jaehyeon.dentalclinic.service.AppointmentQueryService
import me.jaehyeon.dentalclinic.service.AppointmentScheduler
import me.jaehyeon.dentalclinic.service.impl.DefaultAppointmentScheduler
import me.jaehyeon.dentalclinic.service.impl.WeekendAppointmentScheduler
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.DayOfWeek
import java.time.LocalDateTime

@RestController
@RequestMapping("/appointments")
class AppointmentController(
    val appointmentSchedulers: List<AppointmentScheduler>,
    val appointmentQueryService: AppointmentQueryService,
) {
    @PostMapping
    fun scheduleAppointment(
        @Valid @RequestBody appointmentRequest: AppointmentRequest,
    ): ResponseEntity<AppointmentResponse> {
        val appointmentScheduler = findSchedulerFor(appointmentRequest.dateTime)
        val appointmentResult = appointmentScheduler.schedule(appointmentRequest)
        return if (appointmentResult.id != null) {
            ResponseEntity.ok(appointmentResult)
        } else {
            ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(appointmentResult)
        }
    }

    @GetMapping("/{appointmentId}")
    fun getAppointment(
        @PathVariable appointmentId: String,
    ): ResponseEntity<AppointmentDetailDTO> {
        val appointmentDetails = appointmentQueryService.getAppointmentById(appointmentId)
        return if (appointmentDetails != null) {
            ResponseEntity.ok(appointmentDetails)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    private fun findSchedulerFor(date: LocalDateTime): AppointmentScheduler {
        val weekend = listOf(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY)
        return if (date.dayOfWeek in weekend) {
            appointmentSchedulers.first {
                it is WeekendAppointmentScheduler
            }
        } else {
            appointmentSchedulers.first {
                it is DefaultAppointmentScheduler
            }
        }
    }
}
