@file:Suppress("ktlint:standard:filename")

package me.jaehyeon.repository

import me.jaehyeon.model.Destination
import me.jaehyeon.model.destination

object DestinationsRepository {
    private val destinations =
        mutableListOf(
            Destination("Paris", 300.0, "The city of love", setOf("city trip", "europe")),
            Destination("London", 350.0, "The heart of England", setOf("city trip", "UK")),
            Destination("New York", 400.0, "The city that never sleeps", setOf("city trip", "US")),
            Destination("Hanoi", 450.0, "City of lakes and charm", setOf("city trip", "asia")),
        )

    fun filterDestinations(criteria: (Destination) -> Boolean): List<Destination> =
        destinations
            .filter(criteria)
            .sortedBy { it.price }

    fun filterDestinationsMulti(vararg criteria: (Destination) -> Boolean): List<Destination> =
        destinations
            .filter { destination -> criteria.all { it(destination) } }
            .sortedBy { it.price }

    fun addDestination(destination: Destination) {
        destinations.add(destination)
    }
}
