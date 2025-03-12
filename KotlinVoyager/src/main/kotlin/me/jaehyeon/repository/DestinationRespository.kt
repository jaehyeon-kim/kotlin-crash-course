@file:Suppress("ktlint:standard:filename")

package me.jaehyeon.repository

import me.jaehyeon.model.Destination

object DestinationsRepository {
    private val destinations =
        mutableListOf(
            Destination("Paris", 300.0, "The city of love"),
            Destination("London", 350.0, "The heart of England"),
            Destination("New York", 400.0, "The city that never sleeps"),
            Destination("Hanoi", 450.0, "City of lakes and charm"),
        )

    fun filterDestinations(criteria: (Destination) -> Boolean): List<Destination> =
        destinations
            .filter(criteria)
            .sortedBy { it.price }
}
