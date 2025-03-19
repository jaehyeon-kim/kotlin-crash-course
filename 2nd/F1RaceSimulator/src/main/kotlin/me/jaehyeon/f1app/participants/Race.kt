package me.jaehyeon.f1app.participants

import me.jaehyeon.f1app.SafetyCarException
import me.jaehyeon.f1app.YellowFlagException
import kotlin.random.Random

class Race(
    val numberOfLaps: Int,
    val teams: List<Team>,
    var currentLap: Int = 0,
) {
    val raceResults: MutableList<Result> = mutableListOf()

    companion object {
        const val PITSTOP_TIME = 5.0 // 5 minutes
        const val SLOWDOWN_TIME = 1.0 // 1 minute
    }

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
            }
            RaceEvent.COLLISION -> {
                car.isPitStopNeeded = true
                throw SafetyCarException("Car #${car.carNumber} collided - pit stop!")
            }
            RaceEvent.NORMAL -> {
                car.currentLap++
                val lapTime = Random.nextDouble(1.0, 2.0)
                car.addLapTime(car.currentLap, lapTime)
                println("Driver ${driver.name} completed lap in ${"%.2f".format(lapTime)} minutes")
                lapTime
            }
        }

    private fun handlePitStop(result: Result) {
        println("\"Car ${result.car.carNumber} skipts this lap.")
        result.car.isPitStopNeeded = false
        result.totalLapTime += Race.PITSTOP_TIME
    }

    private fun slowDownLapTimes() {
        raceResults.forEach { it.totalLapTime += Race.SLOWDOWN_TIME }
    }

    private fun runLapForDriver(result: Result) {
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

//    fun simulateLap(
//        driver: Driver,
//        car: RaceCar,
//    ): Double {
//        car.currentLap++
//        val lapTime = Random.nextDouble(1.0, 2.0)
//        car.addLapTime(car.currentLap, lapTime)
//        println("Driver ${driver.name} completed lap in ${"%.2f".format(lapTime)} minutes")
//        return lapTime
//    }

    fun findOrAddResult(
        team: Team,
        driver: Driver,
        car: RaceCar,
    ) = raceResults.find { it.driver == driver } ?: Race.Result(team, driver, car).also { raceResults.add(it) }

    fun runLap() {
        teams.forEach { team ->
            team.driverCarMap.forEach { (driver, car) ->
                val result = findOrAddResult(team, driver, car)
                if (car.isPitStopNeeded) {
                    handlePitStop(result)
                } else {
                    runLapForDriver(result)
                }
            }
        }
    }

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

    fun displayTeamLeaderBoard() {
        println("\n--- TEAM LEADERBOARD ---")
        val teamResults = teams.toSortedTeamResults(raceResults)
        teamResults.forEachIndexed { index, result ->
            println(result.format(index))
        }
    }

    private fun List<Team>.toSortedTeamResults(raceResults: List<Race.Result>) =
        map { team ->
            val teamTime = raceResults.filter { it.team == team }.sumOf { it.totalLapTime }
            Race.TeamResult(team, teamTime)
        }.sortedBy { it.totalTime }

    private fun TeamResult.format(index: Int): String {
        val teamPosition = "${index + 1}. Team ${team.name}"
        val teamTime = "with total time $totalTime} minutes"
        val sponsor = team.mainSponsor?.let { "Sponsored by ${it.name}" ?: "No main sponsor" }
        return "$teamPosition $teamTime. $sponsor"
    }

    fun runRace() {
        start()
        end()
    }

    fun start() {
        for (lap in 1..numberOfLaps) {
            currentLap = lap
            println("Starting lap $currentLap")
            runLap()
        }
    }

    fun end() {
        awardPoints()
        displayLeaderboard()
        displayTeamLeaderBoard()
    }

    private fun awardPoints() {
        val pointsList = listOf(25, 18, 15, 12, 10, 8, 6, 4, 2, 1)
        for ((index, result) in raceResults.take(10).withIndex()) {
            val points = pointsList.getOrNull(index) ?: 0
            result.driver.addPoints(points)
        }
    }
}

enum class RaceEvent {
    NORMAL,
    BREAKDOWN,
    COLLISION,
}

fun generateRaceEvent(
    breakdownPercentage: Int = 5,
    collisionPercentage: Int = 2,
): RaceEvent {
    val event =
        Random.nextInt(100).let {
            when {
                it < breakdownPercentage -> RaceEvent.BREAKDOWN // 5% chance of breakdown
                it < (breakdownPercentage + collisionPercentage) -> RaceEvent.COLLISION // 2% chance of collision
                else -> RaceEvent.NORMAL
            }
        }
    return event
}
