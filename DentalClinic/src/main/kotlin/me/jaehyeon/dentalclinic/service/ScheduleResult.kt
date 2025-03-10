package me.jaehyeon.dentalclinic.service

import me.jaehyeon.dentalclinic.model.DentalPractitioner
import me.jaehyeon.dentalclinic.model.Treatment

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

    object TimeSlotUnavailable : ScheduleResult
}
