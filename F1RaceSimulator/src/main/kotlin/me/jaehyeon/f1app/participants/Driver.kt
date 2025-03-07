@file:Suppress("ktlint:standard:no-wildcard-imports")

package me.jaehyeon.f1app.participants

import java.util.*
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

class Driver(
    val name: String,
    var points: Int = 0,
    val uuid: UUID = UUID.randomUUID(),
) {
    override fun toString(): String = "Driver(name='$name', points=$points)"

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Driver

        return uuid == other.uuid
    }

    override fun hashCode(): Int = uuid.hashCode()

    fun addPoints(newPoints: Int) {
        points += newPoints
    }
}

class RaceCar(
    val carNumber: Int,
    val manufacturer: String? = null,
    val maxSpeed: Double = Random.nextDouble(200.0, 230.0),
    private var currentSpeed: Double = 0.0,
    internal var currentLab: Int = 0,
    internal var isPitStopNeeded: Boolean = false,
    numLaps: Int,
) {
    var lapTimes = arrayOfNulls<Double>(numLaps)

    fun addLapTime(
        lapNumber: Int,
        time: Double,
    ) {
        lapTimes[lapNumber - 1] = time
    }

    override fun toString(): String =
        "RaceCar(carNumber=$carNumber, manufacturer=$manufacturer, maxSpeed=$maxSpeed, currentSpeed=$currentSpeed, currentLab=$currentLab, isPitStopNeeded=$isPitStopNeeded, lapTimes=${lapTimes.contentToString()})"
}

data class Sponsor(
    val name: String,
    val amount: Double,
)

fun getRandomSponsor(): Sponsor? {
    val randomNumber = Random.nextInt(0, 11)
    return if (randomNumber == 0) {
        null
    } else {
        Sponsor(
            name = "Sponsr$randomNumber",
            amount = Random.nextDouble(1_000_000.0, 10_000_000.0),
        )
    }
}
