package me.jaehyeon.voyager

import me.jaehyeon.model.DiscountApplier
import me.jaehyeon.model.destination
import me.jaehyeon.repository.DestinationsRepository
import me.jaehyeon.service.applyDiscount
import me.jaehyeon.service.toDestinationTags

fun main() {
    val minPrice: Double = 0.0
    val maxPrice: Double = 400.0

    val discountApplier =
        DiscountApplier { destination ->
            destination.copy(price = destination.price * 0.9)
        }

    val filteredDestinations =
        DestinationsRepository.filterDestinations {
            it.price in minPrice..maxPrice
        }

    println("\nfilteredDestinations>>")
    for (d in filteredDestinations) println(d)

//    val discountedDestinations =
//        applyDiscount(
//            discountApplier,
//            filteredDestinations,
//        ).also { println("\nApply 10% discount...") }

    val discountedDestinations =
        filteredDestinations
            .applyDiscount(discountApplier)
            .also { println("\nApply 10% discount...") }

    println("\ndiscountedDestinations>>")
    for (d in discountedDestinations) println(d)

    val dest1 = filteredDestinations[0]
    val dest2 = filteredDestinations[2]

    println("\ninfix + operator overloading>>")
    println(dest1 combineWith dest2)
    println(dest1 + dest2)

    println("\ndestination builder>>")
    val tagsList = setOf("city trip", "asia")
    val newDestination =
        destination {
            name = "Seoul"
            price = 500.0
            description = "The city of K Culture"
            tags(*tagsList.toTypedArray())
        }
    DestinationsRepository.addDestination(newDestination)
    for (d in DestinationsRepository.filterDestinations { it.price > 0.0 }) println(d)

    val min = 350.0
    val max = 500.0
    val tags = "city tour, asia, europe".toDestinationTags()
    val filteredDestinationsMulti =
        DestinationsRepository.filterDestinationsMulti(
            { it.price in min..max },
            { tags.isEmpty() || it.tags.intersect(tags).isNotEmpty() },
        )
    println("\nfilter destinations multi>>")
    for (d in filteredDestinationsMulti) println(d)
}
