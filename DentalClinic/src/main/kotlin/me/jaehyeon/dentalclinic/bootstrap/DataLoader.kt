package me.jaehyeon.dentalclinic.bootstrap

import me.jaehyeon.dentalclinic.model.*
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class DataLoader {
    @EventListener(ContextRefreshedEvent::class)
    fun populateInitialData() {
        val patient = Patient("P001", "John Doe")
        Clinic.patients.addPerson(patient)
        val hygienist = Hygienist("DP001", "Ms. Claire")
        Clinic.hygienists.addPerson(hygienist)
        val dentist = Dentist("DP002", "Dr. Smith")
        Clinic.dentists.addPerson(dentist)
        Clinic.addTreatment(Treatment.teethCleaning())
    }
}
