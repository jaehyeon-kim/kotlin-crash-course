package me.jaehyeon.dentalclinic.service

import me.jaehyeon.dentalclinic.api.dto.AppointmentRequest
import me.jaehyeon.dentalclinic.api.dto.AppointmentResponse
import me.jaehyeon.dentalclinic.model.Clinic

interface AppointmentScheduler {
    fun schedule(appointmentRequest: AppointmentRequest): AppointmentResponse {
        val result =
            Clinic.scheduleAppointment(
                appointmentRequest.patientId,
                appointmentRequest.dentalPractitionerId,
                appointmentRequest.dateTime,
                appointmentRequest.treatmentId,
            )

        return when (result) {
            is ScheduleResult.Success ->
                AppointmentResponse(
                    id = result.appointmentId,
                    message = successMessage(),
                )

            is ScheduleResult.TimeSlotUnavailable ->
                AppointmentResponse(
                    message = "Time slot unavailable!",
                )

            is ScheduleResult.TreatmentUnavailable -> {
                val ids =
                    result.availableTreatments.map { it.id }
                val msg =
                    "Treatment unavailable! Available treatments: $ids"
                AppointmentResponse(message = msg)
            }

            is ScheduleResult.DentalPractitionerUnavailable -> {
                val ids =
                    result.availableDentalPractitioners.map { it.id }
                val msg =
                    "Practitioner unavailable! Who is available: $ids"
                AppointmentResponse(message = msg)
            }

            is ScheduleResult.NotFound ->
                AppointmentResponse(
                    message = result.message,
                )
        }
    }

    fun successMessage(): String
}

// interface AppointmentScheduler {
//    fun schedule(appointmentRequest: AppointmentRequest): AppointmentResponse =
//        AppointmentResponse(id = UUID.randomUUID().toString(), message = successMessage())
//
//    fun successMessage(): String
// }
