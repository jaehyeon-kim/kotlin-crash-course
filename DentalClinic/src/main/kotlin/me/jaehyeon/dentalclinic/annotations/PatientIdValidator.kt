package me.jaehyeon.dentalclinic.annotations

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

class PatientIdValidator : ConstraintValidator<ValidPatientId, String> {
    // regex pattern for string that begins with "P" followed by numbers
    private val idPattern = "P\\d+".toRegex()

    override fun isValid(
        value: String?,
        context: ConstraintValidatorContext?,
    ): Boolean = value != null && value.matches(idPattern)
}
