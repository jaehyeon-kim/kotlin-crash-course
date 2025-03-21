package me.jaehyeon.clinic.model

import me.jaehyeon.clinic.service.PersonManager
import java.time.LocalDateTime
import java.util.UUID

object Clinic {
    val patients = PersonManager<Patient>()
    val dentists = PersonManager<Dentist>()
    val hygienists = PersonManager<Hygienist>()
    val treatments = mutableMapOf<String, Treatment>()
    val appointments = mutableListOf<Appointment>()

    fun addPatient(patient: Patient) = patients.addPerson(patient)

    fun addDentist(dentist: Dentist) = dentists.addPerson(dentist)

    fun addTreatment(treatment: Treatment) = treatments.put(treatment.id, treatment)

    fun scheduleAppointment(
        patientId: String,
        dentalPractitionerId: String,
        time: LocalDateTime,
        treatmentId: String,
    ) {
        val patient =
            checkNotNull(patients.getPersonById(patientId)) {
                // throw IllegalStateException with the following message.
                "Patient not found"
            }
        val dentist = checkNotNull(dentists.getPersonById(dentalPractitionerId)) { "Dentist not found" }
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

    private fun isAvailable(person: DentalPractitioner): Boolean = Character.getNumericValue(person.id.last()) % 2 == 0
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
