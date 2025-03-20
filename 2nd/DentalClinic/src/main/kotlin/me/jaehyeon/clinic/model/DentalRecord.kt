package me.jaehyeon.clinic.model

class DentalRecord(
    val patientId: String,
) {
    // Use backing properties for mutable fields to encapsulate state changes.
    private val _treatmentHistory = mutableListOf<Treatment>()
    val treatmentHistory: List<Treatment>
        get() = _treatmentHistory

    fun addTreatment(treatment: Treatment) {
        _treatmentHistory.add(treatment)
    }
}
