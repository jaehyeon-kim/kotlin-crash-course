package me.jaehyeon.dentalclinic.model

import java.util.*

class Receptionist(
    id: String,
    name: String,
    override val role: String = "Receptionist",
) : Person(id, name),
    Employee {
    override fun scheduleLeave(
        startDate: Date,
        endDate: Date,
    ) {
        println("Receptionist $name scheduled leave from $startDate to $endDate.")
    }
}
