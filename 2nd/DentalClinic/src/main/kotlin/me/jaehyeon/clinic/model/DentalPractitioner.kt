package me.jaehyeon.clinic.model

interface DentalPractitioner {
    val id: String

    fun performTreatment(
        patient: Patient,
        treatment: Treatment,
    ): String
}
