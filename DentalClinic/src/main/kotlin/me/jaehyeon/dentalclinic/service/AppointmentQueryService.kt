package me.jaehyeon.dentalclinic.service

import me.jaehyeon.dentalclinic.api.dto.AppointmentDetailDTO
import me.jaehyeon.dentalclinic.model.Clinic
import org.springframework.stereotype.Service

@Service
class AppointmentQueryService {
    fun getAppointmentById(appointmentId: String): AppointmentDetailDTO? {
        val appointment = Clinic.getAppointmentById(appointmentId) ?: return null
        // destructuring declaration extracts specific properties
        val (_, patient, practitioner, time, treatment) = appointment
        return AppointmentDetailDTO(
            time,
            patient.id,
            practitioner.id,
            treatment.type.displayName,
            treatment.type.duration.inWholeMinutes,
        )
    }
}
