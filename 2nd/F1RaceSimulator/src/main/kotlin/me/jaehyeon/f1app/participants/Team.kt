package me.jaehyeon.f1app.participants

import kotlin.random.Random

class Team(
    val name: String,
    val drivers: List<Driver>,
    val raceCars: Set<RaceCar>,
    val mainSponsor: Sponsor? = getRandomSponsor(),
) {
    val driverCarMap: Map<Driver, RaceCar> = drivers.zip(raceCars).toMap()

    override fun toString(): String = "Team(name='$name', drivers=$drivers, raceCars=$raceCars, mainSponsor=$mainSponsor)"
}

fun getRandomSponsor(): Sponsor? {
    val randomNumber = Random.nextInt(0, 11)
    return if (randomNumber == 0) {
        null
    } else {
        Sponsor("Sponsor$randomNumber", Random.nextDouble(1_000_000.0, 10_000_000.0))
    }
}
