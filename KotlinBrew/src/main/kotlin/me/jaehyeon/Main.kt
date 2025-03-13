@file:Suppress("ktlint:standard:no-wildcard-imports")

package me.jaehyeon

import me.jaehyeon.brewing.*
import kotlin.random.Random

fun main() {
    val brews = listOf(BasicBrew(), CappuccinoBrew(), EspressoBrew())

    println("\nfunction delegation>>")
    for (brew in brews) {
        val coffeeBrewer = CoffeeBrewer(brew)
        println(coffeeBrewer.brewCoffee())
    }

    println("\nproperty delegation>>")
    for (brew in brews) {
        val coffeeBrewer = CoffeeBrewer(brew)
        coffeeBrewer.sugarLevel = Random.nextInt(0, 10)
        println(coffeeBrewer.brewCoffeeWithAddOns())
    }
}
