package me.jaehyeon.dentalclinic.model

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class PerformTreatmentTest {
    private lateinit var patient: Patient
    private lateinit var treatment: Treatment
    private lateinit var dentist: Dentist
    private lateinit var hygienist: Hygienist
    private lateinit var dentalPractitioners: List<DentalPractitioner>

    @BeforeEach
    fun setUp() {
        patient = Patient("1", "Bob")
        treatment = Treatment.teethCleaning()
        dentist = Dentist("2", "Jane")
        hygienist = Hygienist("3", "John")
        dentalPractitioners = listOf(dentist, hygienist)
    }

    @Test
    fun performTreatment() {
        dentalPractitioners.forEach {
            val result = it.performTreatment(patient, treatment)
        }
    }

    private fun assertTreatmentResult(
        practitioner: DentalPractitioner,
        result: String,
    ) {
        when (practitioner) {
            is Dentist -> assertEquals("Dentist Jane is performing Teeth Cleaning", result)
            is Hygienist -> assertEquals("Hygienist John is performing Teeth Cleaning", result)
            else -> NotImplementedError()
        }
    }
}
