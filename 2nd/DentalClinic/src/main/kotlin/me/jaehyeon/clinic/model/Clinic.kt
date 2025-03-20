package me.jaehyeon.clinic.model

import java.time.LocalDateTime
import java.util.UUID

object Clinic {
    val patients = mutableMapOf<String, Patient>()
    val dentists = mutableMapOf<String, Dentist>()
    val treatments = mutableMapOf<String, Treatment>()
    val appointments = mutableListOf<Appointment>()

    fun addPatient(patient: Patient) = patients.put(patient.id, patient)

    fun addDentist(dentist: Dentist) = dentists.put(dentist.id, dentist)

    fun addTreatment(treatment: Treatment) = treatments.put(treatment.id, treatment)

    fun scheduleAppointment(
        patientId: String,
        dentistId: String,
        time: LocalDateTime,
        treatmentId: String,
    ) {
        val patient =
            checkNotNull(patients[patientId]) {
                // throw IllegalStateException with the following message.
                "Patient not found"
            }
        val dentist = checkNotNull(dentists[dentistId]) { "Dentist not found" }
        val treatment = checkNotNull(treatments[treatmentId]) { "Treatment not found" }
        appointments.add(
            Appointment(
                UUID.randomUUID().toString(),
                patient,
                dentist,
                time,
                treatment,
            ),
        )
    }
}
