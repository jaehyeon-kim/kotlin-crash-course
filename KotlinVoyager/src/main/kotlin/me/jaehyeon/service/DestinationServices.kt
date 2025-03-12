package me.jaehyeon.service

import me.jaehyeon.model.Destination
import me.jaehyeon.model.DiscountApplier

// fun applyDiscount(
//    discountApplier: DiscountApplier,
//    destinations: List<Destination>,
// ): List<Destination> = destinations.map(discountApplier::invoke)

fun List<Destination>.applyDiscount(discountApplier: DiscountApplier): List<Destination> = map(discountApplier::invoke)
