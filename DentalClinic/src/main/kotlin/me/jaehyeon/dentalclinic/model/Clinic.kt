@file:Suppress("ktlint:standard:no-wildcard-imports")

package me.jaehyeon.dentalclinic.model

import me.jaehyeon.dentalclinic.service.PersonManager
import me.jaehyeon.dentalclinic.service.ScheduleResult
import java.time.LocalDateTime
import java.util.*

object Clinic {
    // val patients = mutableMapOf<String, Patient>()
    // val dentists = mutableMapOf<String, Dentist>()
    val patients = PersonManager<Patient>()
    val dentists = PersonManager<Dentist>()
    val hygienists = PersonManager<Hygienist>()
    val treatments = mutableMapOf<String, Treatment>()
    val appointments = mutableListOf<Appointment>()

    // fun addPatient(patient: Patient) = patients.put(patient.id, patient)
    // fun addDentist(dentist: Dentist) = dentists.put(dentist.id, dentist)

    fun addTreatment(treatment: Treatment) = treatments.put(treatment.id, treatment)

    fun scheduleAppointment(
        patientId: String,
        dentalPractitionerId: String,
        time: LocalDateTime,
        treatmentId: String,
    ): ScheduleResult {
        // Fetch the patient using the provided ID.
        // Return an error if not found.
        val patient =
            patients.getPersonById(patientId)
                ?: return ScheduleResult.NotFound(
                    "Patient not found",
                )

        val dentalPractitioner: DentalPractitioner =
            findPersonAcrossManagers(
                dentalPractitionerId,
                dentists,
                hygienists,
            ) ?: return ScheduleResult.NotFound(
                "Dental practitioner not found",
            )

        val treatment =
            treatments[treatmentId]
                ?: return ScheduleResult.TreatmentUnavailable(
                    treatments.values.toList(),
                )

        // If the dental practitioner is found and is available,
        // schedule the appointment.
        if (isAvailable(dentalPractitioner)) {
            val appointmentId = UUID.randomUUID().toString()
            appointments.add(
                Appointment(
                    appointmentId,
                    patient,
                    dentalPractitioner,
                    time,
                    treatment,
                ),
            )
            return ScheduleResult.Success(appointmentId)
        } else {
            // If the dental practitioner is not available,
            // return a list of available practitioners.
            val availableDentists =
                dentists.findPersonsByCriteria {
                    isAvailable(it)
                }
            val availableHygienists =
                hygienists.findPersonsByCriteria {
                    isAvailable(it)
                }
            return ScheduleResult.DentalPractitionerUnavailable(
                availableDentists + availableHygienists,
            )
        }

//        val patient = checkNotNull(patients[patientId]) { "Patient not found" }
//        val dentist = checkNotNull(dentists[dentalPractitionerId]) { "Dentist not found" }
//        val treatment = checkNotNull(treatments[treatmentId]) { "Treatment not found" }
//        appointments.add(Appointment(UUID.randomUUID().toString(), patient, dentist, time, treatment))
    }

    private fun isAvailable(person: DentalPractitioner): Boolean = Character.getNumericValue(person.id.last()) % 2 == 0

    fun getAppointmentById(id: String): Appointment? = appointments.find { it.id == id }
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
