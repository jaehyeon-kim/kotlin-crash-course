package me.jaehyeon.clinic.model

class Patient(
    override val id: String,
    override val name: String,
) : Person(id, name) {
    var insurance: String? = null
    lateinit var dentalRecord: DentalRecord

    init {
        println("Patient initialized with id $id and name: $name")
    }

    constructor(id: String, name: String, insurance: String) : this(id, name) {
        println("Secondary constructor called. Insurance: $insurance")
        this.insurance = insurance
    }

    fun receiveTreatment(treatment: Treatment) {
        if (!::dentalRecord.isInitialized) {
            dentalRecord = DentalRecord(id)
        }
        println("Patient $name is receiving ${treatment.name}")
        dentalRecord.addTreatment(treatment)
    }

    override fun introduce() = "${super.introduce()} I am a patient."
}
