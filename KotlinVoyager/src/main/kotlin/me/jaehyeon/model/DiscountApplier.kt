package me.jaehyeon.model

fun interface DiscountApplier {
    operator fun invoke(destination: Destination): Destination
}
