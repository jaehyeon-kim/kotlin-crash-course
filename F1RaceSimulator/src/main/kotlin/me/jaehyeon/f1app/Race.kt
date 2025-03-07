package me.jaehyeon.f1app

import me.jaehyeon.f1app.participants.Driver
import me.jaehyeon.f1app.participants.RaceCar
import me.jaehyeon.f1app.participants.Team
import kotlin.random.Random

enum class RaceEvent {
    NORMAL,
    BREAKDOWN,
    COLLISION,
}

fun generateRaceEvent(
    breakdownPercent: Int = 5,
    collisionPercent: Int = 2,
): RaceEvent {
    val totalExceptionPercent = breakdownPercent + collisionPercent
    val event =
        Random.nextInt(100).let {
            when {
                it < breakdownPercent -> RaceEvent.BREAKDOWN // A 5% chance of a breakdown
                it < totalExceptionPercent -> RaceEvent.COLLISION // A 2 % chance of a collision
                else -> RaceEvent.NORMAL
            }
        }
    return event
}

class Race(
    val numberOfLabs: Int,
    val teams: List<Team>,
    var currentLap: Int = 0,
) {
    val raceResults: MutableList<Result> = mutableListOf()

    data class Result(
        val team: Team,
        val driver: Driver,
        val car: RaceCar,
        var totalLapTime: Double = 0.0,
        var fastestLap: Double = Double.MAX_VALUE,
    )

    data class TeamResult(
        val team: Team,
        val totalTime: Double,
    )

    private fun simulateLap(
        driver: Driver,
        car: RaceCar,
    ): Double =
        when (generateRaceEvent()) {
            RaceEvent.BREAKDOWN -> {
                car.isPitStopNeeded = true
                throw YellowFlagException("Car #${car.carNumber} broke down - pit stop!")
                // Race.PITSTOP_TIME
            }
            RaceEvent.COLLISION -> {
                car.isPitStopNeeded = true
                throw SafetyCarException("Car ${car.carNumber} collided - pit stop!")
                // Race.PITSTOP_TIME
            }
            RaceEvent.NORMAL -> {
                car.currentLab++
                val lapTime = Random.nextDouble(1.0, 2.0)
                car.addLapTime(car.currentLab, lapTime)
                println("Driver ${driver.name} completed lap in ${"%.2f".format(lapTime)} minutes.")
                lapTime
            }
        }

//    fun simulateLap(
//        driver: Driver,
//        car: RaceCar,
//    ): Double {
//        car.currentLab++
//        val lapTime = Random.nextDouble(1.0, 2.0)
//        car.addLapTime(car.currentLab, lapTime)
//        println("Driver ${driver.name} completed lap in ${"%.2f".format(lapTime)} minutes.")
//        return lapTime
//    }

    fun findOrAddResult(
        team: Team,
        driver: Driver,
        car: RaceCar,
    ) = raceResults.find { it.driver == driver } ?: Race.Result(team, driver, car).also { raceResults.add(it) }

    private fun runLap() {
        teams.forEach { team ->
            team.driverCarMap.forEach { (driver, car) ->
                val result = findOrAddResult(team, driver, car)
                // If the car needs a pit stop, skip this lap for the driver
                if (car.isPitStopNeeded) {
                    handlePitStop(result)
                } else {
                    runLapForDriver(result)
                }
            }
        }
    }

//    fun runLap() {
//        teams.forEach { team ->
//            team.driverCarMap.forEach { (driver, car) ->
//                val result = findOrAddResult(team, driver, car)
//                // If the car needs a pit stop, skip this lap for the driver
//                if (car.isPitStopNeeded) {
//                    println("Car ${car.carNumber} skips this lap.")
//                    car.isPitStopNeeded = false
//                } else {
//                    val lapTime = simulateLap(driver, car)
//                    result.totalLapTime += lapTime
//                    if (lapTime < result.fastestLap) {
//                        result.fastestLap = lapTime
//                    }
//                }
//            }
//        }
//    }

    fun displayLeaderboard() {
        println("\n--- LEADERBOARD ---")
        raceResults
            .sortedBy { it.totalLapTime }
            .forEachIndexed { index, result ->
                val leaderboardEntry =
                    """
                    |${index + 1}. Driver ${result.driver.name}
                    |in car #${result.car.carNumber}
                    |from team ${result.team.name}
                    |with total time ${"%.2f".format(result.totalLapTime)} minutes
                    |(fastest lap: ${"%.2f".format(result.fastestLap)} minutes)
                    """.trimMargin()
                println(leaderboardEntry)
            }
    }

    fun start() {
        for (lap in 1..numberOfLabs) {
            currentLap = lap
            println("Starting lab $currentLap")
            runLap()
        }
    }

    fun end() {
        awardPoints()
        displayLeaderboard()
        displayTeamLeaderBoard()
    }

//    fun displayTeamLeaderBoard() {
//        println("\n--- TEAM LEADERBOARD ---")
//        val teamResults =
//            teams
//                .map { team ->
//                    val teamTime =
//                        raceResults
//                            .filter { it.team == team }
//                            .sumOf { it.totalLapTime }
//                    Race.TeamResult(team, teamTime)
//                }.sortedBy { it.totalTime }
//        teamResults.forEachIndexed { index, result ->
//            val teamPosition = "${index + 1}. Team ${result.team.name}"
//            val teamTime = "with total time ${"%.2f".format(result.totalTime)} minutes"
//            println("$teamPosition $teamTime")
//        }
//    }

    fun displayTeamLeaderBoard() {
        println("\n--- TEAM LEADERBOARD ---")
        val teamResults = teams.toSortedTeamResults(raceResults)
        teamResults.forEachIndexed { index, result ->
            println(result.format(index))
        }
    }

    fun runRace() {
        start()
        end()
    }

    private fun List<Team>.toSortedTeamResults(raceResults: List<Race.Result>) =
        map { team ->
            val teamTime = raceResults.filter { it.team == team }.sumOf { it.totalLapTime }
            Race.TeamResult(team, teamTime)
        }.sortedBy { it.totalTime }

    private fun TeamResult.format(index: Int): String {
        val teamPosition = "${index + 1}. Team ${team.name}"
        val teamTime = "with total time ${"%.2f".format(totalTime)} minutes"
        val sponsor = team.mainSponsor?.let { "Sponsored by ${it.name}" } ?: "No main sponsor"
        return "$teamPosition $teamTime. $sponsor"
    }

    private fun awardPoints() {
        // Points corresponding to the positions 1st through 10th
        val pointsList = listOf(25, 18, 15, 12, 10, 8, 6, 4, 2, 1)
        // Award points to the top 10 finishers
        for ((index, result) in raceResults.take(10).withIndex()) {
            // The points for this position
            // are at the same index in the pointsListFundamental Building Blocks of Kotlin  137
            val points = pointsList.getOrNull(index) ?: 0
            result.driver.addPoints(points)
        }
    }

    private fun handlePitStop(result: Result) {
        println("\"Car ${result.car.carNumber} skips this lap.")
        result.car.isPitStopNeeded = false
        result.totalLapTime += Race.PITSTOP_TIME
    }

    private fun slowDownLapTimes() {
        // Increase total lap time for drivers to simulate a race slowdown
        raceResults.forEach { it.totalLapTime += Race.SLOWDOWN_TIME }
    }

    private fun runLapForDriver(result: Race.Result) {
        try {
            val lapTime = simulateLap(result.driver, result.car)
            result.totalLapTime += lapTime
            if (lapTime < result.fastestLap) {
                result.fastestLap = lapTime
            }
        } catch (e: SafetyCarException) {
            println("${e.message} Safety car deployed.")
            slowDownLapTimes()
        } catch (e: YellowFlagException) {
            println("${e.message} Yellow flag raised.")
            slowDownLapTimes()
        }
    }

    companion object {
        const val PITSTOP_TIME = 5.0 // 5 minutes
        const val SLOWDOWN_TIME = 1.0 // 1 minute
    }
}
