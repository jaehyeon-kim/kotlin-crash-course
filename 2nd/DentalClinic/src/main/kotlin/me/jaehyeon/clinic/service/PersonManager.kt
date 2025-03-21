package me.jaehyeon.clinic.service

import me.jaehyeon.clinic.model.Person

class PersonManager<T : Person> {
    private val persons = mutableMapOf<String, T>()

    fun addPerson(person: T) {
        PersonIdRegistry.registerID(person.id)
        persons[person.id] = person
    }

    fun removePerson(id: String) {
        persons.remove(id)?.let { PersonIdRegistry.unregisterID(it.id) }
    }

    fun getPersonById(id: String): T? = persons[id]

    fun findPersonsByCriteria(criteria: (T) -> Boolean): List<T> = persons.values.filter(criteria)
}
