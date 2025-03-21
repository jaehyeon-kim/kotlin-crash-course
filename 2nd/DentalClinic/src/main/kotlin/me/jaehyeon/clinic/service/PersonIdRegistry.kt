package me.jaehyeon.clinic.service

object PersonIdRegistry {
    private val registeredIDs = mutableSetOf<String>()

    fun registerID(id: String) {
        // throw IllegalArgumentException if id exists
        require(registeredIDs.add(id)) { "ID already exists: $id" }
    }

    fun unregisterID(id: String) {
        registeredIDs.remove(id)
    }
}
