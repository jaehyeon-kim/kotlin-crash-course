package me.jaehyeon.brewing

interface Brew {
    val name: String

    fun brewCoffee(): String
}

class BasicBrew : Brew {
    override val name = "Basic Brew"

    override fun brewCoffee(): String = "Brewing a basic coffee."
}

class CappuccinoBrew : Brew {
    override val name = "Cappuccino"

    override fun brewCoffee(): String = "Brewing a frothy cappuccino."
}

class EspressoBrew : Brew {
    override val name = "Espresso"

    override fun brewCoffee(): String = "Brewing a rich espresso."
}

class CoffeeBrewer(
    val brew: Brew,
) : Brew by brew {
    var sugarLevel: Int by SugarDelegate()

    fun brewCoffeeWithAddOns(): String {
        val coffee = brewCoffee()
        val sugar = "Adding $sugarLevel spoon(s) of sugar."
        return if (sugarLevel > 0) "$coffee $sugar" else coffee
    }
}
