@file:Suppress("ktlint:standard:no-wildcard-imports")

package me.jaehyeon.dentalclinic.model

import me.jaehyeon.dentalclinic.service.PersonManager
import java.time.LocalDateTime
import java.util.*

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
        val patient = checkNotNull(patients[patientId]) { "Patient not found" }
        val dentist = checkNotNull(dentists[dentistId]) { "Dentist not found" }
        val treatment = checkNotNull(treatments[treatmentId]) { "Treatment not found" }

        appointments.add(Appointment(UUID.randomUUID().toString(), patient, dentist, time, treatment))
    }
}

inline fun <reified T> findPersonAcrossManagers(
    id: String,
    vararg managers: PersonManager<*>,
): T? {
    for (manager in managers) {
        val person = manager.getPersonById(id)
        if (person != null && person is T) {
            return person
        }
    }
    return null
}
