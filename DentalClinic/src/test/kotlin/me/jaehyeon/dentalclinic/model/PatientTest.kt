@file:Suppress("ktlint:standard:no-wildcard-imports")

package me.jaehyeon.dentalclinic.model

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class PatientTest {
    @Test
    fun introduce() {
        val patient = Patient("1", "John", "Insurance1")
        val patientIntro = "Hello, my name is John. I am a patient."
        assertEquals(patientIntro, patient.introduce())
    }
}
