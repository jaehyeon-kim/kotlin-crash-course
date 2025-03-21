package me.jaehyeon.clinic.service

import me.jaehyeon.clinic.model.DentalPractitioner
import me.jaehyeon.clinic.model.Treatment

sealed interface ScheduleResult {
    data class Success(
        val appointmentId: String,
    ) : ScheduleResult

    data class DentalPractitionerUnavailable(
        val availableDentalPractitioners: List<DentalPractitioner>,
    ) : ScheduleResult

    data class TreatmentUnavailable(
        val availableTreatments: List<Treatment>,
    ) : ScheduleResult

    data class NotFound(
        val message: String,
    ) : ScheduleResult

    object TimeSlopUnavailable : ScheduleResult
}
