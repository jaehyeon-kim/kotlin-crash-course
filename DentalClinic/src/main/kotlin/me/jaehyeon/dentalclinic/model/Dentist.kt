package me.jaehyeon.dentalclinic.model

import java.util.Date

class Dentist(
    id: String,
    name: String,
    override val role: String = "Dentist",
) : Person(id, name),
    Employee,
    DentalPractitioner {
    var treatmentCount = 0
        private set

    override fun scheduleLeave(
        startDate: Date,
        endDate: Date,
    ) {
        println("Dentist $name scheduled leave from $startDate to $endDate.")
    }

    override fun performTreatment(
        patient: Patient,
        treatment: Treatment,
    ): String {
        val message = "Dentist $name is performing ${treatment.name}"
        println(message)
        treatmentCount++
        // additional code to handle the treatment process
        patient.receiveTreatment(treatment)
        return message
    }

    override fun introduce() = "${super.introduce()} I am a dentist."
}
