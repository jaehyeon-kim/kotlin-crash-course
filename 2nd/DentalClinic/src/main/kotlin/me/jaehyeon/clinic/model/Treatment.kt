@file:Suppress("ktlint:standard:no-wildcard-imports")

package me.jaehyeon.clinic.model

import kotlinx.serialization.*

@Serializable
data class Treatment(
    val id: String,
    val name: String,
    val type: TreatmentType,
) {
    val duration = type.duration

    // companion object to define class level properties and functions
    // such as constants and factory methods
    companion object {
        fun teethCleaning() =
            Treatment(
                "T001",
                "Teeth Cleaning",
                TreatmentType.CLEANING,
            )

        fun cavityFilling() =
            Treatment(
                "T002",
                "Cavity Filling",
                TreatmentType.FILLING,
            )

        fun rootCanal() =
            Treatment(
                "T003",
                "Root Canal",
                TreatmentType.ROOT_CANAL,
            )
    }
}

// import kotlinx.serialization.json.Json
// import me.jaehyeon.clinic.model.Treatment
//
// val treatment = Treatment("id", "name")
// val jsonString = Json.encodeToString(treatment)
// println(jsonString)
// println(Json.decodeFromString<Treatment>(jsonString))
