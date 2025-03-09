package me.jaehyeon.dentalclinic.model

interface DentalPractitioner {
    val id: String

    fun performTreatment(
        patient: Patient,
        treatment: Treatment,
    ): String
}
