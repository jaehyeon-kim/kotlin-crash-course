package me.jaehyeon.voyager

import me.jaehyeon.model.DiscountApplier
import me.jaehyeon.repository.DestinationsRepository
import me.jaehyeon.service.applyDiscount

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
}
