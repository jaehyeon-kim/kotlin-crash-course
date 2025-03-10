package me.jaehyeon.dentalclinic.model

open class Person(
    open val id: String,
    open val name: PersonName,
    open val email: Email?,
) {
    open fun introduce() = "Hello, my name is $name."

    constructor(id: String, rawName: String) : this(id, PersonName(rawName), null)
}

@JvmInline
value class PersonName(
    val value: String,
) {
    init {
        require(value.isNotBlank()) {
            "Name cannot be blank"
        }
        require(!value.any { it.isDigit() }) {
            "Name cannot contain numbers"
        }
    }

    override fun toString() = value
}

@JvmInline
value class Email(
    val email: String,
)
