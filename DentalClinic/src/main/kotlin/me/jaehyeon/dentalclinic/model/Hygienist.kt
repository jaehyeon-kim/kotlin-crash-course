package me.jaehyeon.dentalclinic.model

import java.util.Date

class Hygienist(
    id: String,
    name: String,
    override val role: String = "Hygienist",
) : Person(id, name),
    Employee,
    DentalPractitioner {
    override fun scheduleLeave(
        startDate: Date,
        endDate: Date,
    ) {
        println("Hygienist $name scheduled leave from $startDate to $endDate.")
    }

    override fun performTreatment(
        patient: Patient,
        treatment: Treatment,
    ): String {
        val message = "Hygienist $name is performing ${treatment.name}"
        println(message)
        patient.receiveTreatment(treatment)
        return message
    }
}
