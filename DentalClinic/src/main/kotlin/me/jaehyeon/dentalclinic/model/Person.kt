package me.jaehyeon.dentalclinic.model

open class Person(
    open val id: String,
    open val name: String,
) {
    open fun introduce() = "Hello, my name is $name."
}
