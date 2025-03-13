package me.jaehyeon.f1app

import me.jaehyeon.f1app.participants.Driver
import me.jaehyeon.f1app.participants.RaceCar
import me.jaehyeon.f1app.participants.Team
import kotlin.random.Random

class Race(
    val numberOfLaps: Int,
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

    private fun runLap() {
        teams.forEach { team ->
            team.driverCarMap.forEach { (driver, car) ->
                val result = findOrAddResult(team, driver, car)
                // If the car needs a pit stop, we skip this lap for the driver
                if (car.isPitStopNeeded) {
                    handlePitStop(result)
                } else {
                    runLapForDriver(result)
                }
            }
        }
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

    private fun handlePitStop(result: Result) {
        println("\"Car ${result.car.carNumber} skips this lap.")
        // reset the flag
        result.car.isPitStopNeeded = false
        // add pit stop time
        result.totalLapTime += PITSTOP_TIME
    }

    private fun slowDownLapTimes() {
        // Increase lap times for all drivers to simulate a race slowdown
        raceResults.forEach { it.totalLapTime += SLOWDOWN_TIME }
    }

    private fun simulateLap(
        driver: Driver,
        car: RaceCar,
    ): Double =
        when (generateRaceEvent()) {
            RaceEvent.BREAKDOWN -> {
                car.isPitStopNeeded = true
                throw YellowFlagException(
                    "Car ${car.carNumber} broke down - pit stop!",
                )
            }

            RaceEvent.COLLISION -> {
                car.isPitStopNeeded = true
                throw SafetyCarException(
                    "Car #${car.carNumber} collided - pit stop!",
                )
            }

            RaceEvent.NORMAL -> {
                car.currentLap++
                val lapTime = Random.nextDouble(1.0, 2.0)
                car.addLapTime(car.currentLap, lapTime)
                println("Driver ${driver.name} completed lap: $lapTime min")
                lapTime
            }
        }

    fun findOrAddResult(
        team: Team,
        driver: Driver,
        car: RaceCar,
    ) = raceResults.find { it.driver == driver }
        ?: Result(team, driver, car).also { raceResults.add(it) }

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
                |with total time ${result.totalLapTime} minutes
                |(fastest lap: ${result.fastestLap} minutes)
                    """.trimMargin()
                println(leaderboardEntry)
            }
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
        displayTeamLeaderboard()
    }

    /**
     * Awards points to the top 10 finishers.
     */
    private fun awardPoints() {
        // Points corresponding to the positions 1st through 10th.
        val pointsList = listOf(25, 18, 15, 12, 10, 8, 6, 4, 2, 1)

        // Award points to the top 10 finishers
        for ((index, result) in raceResults.take(10).withIndex()) {
            // The points for this position
            // are at the same index in the pointsList
            val points = pointsList.getOrNull(index) ?: 0
            result.driver.addPoints(points)
        }
    }

    fun displayTeamLeaderboard() {
        println("\n--- TEAM LEADERBOARD ---")
        val teamResults = teams.toSortedTeamResults(raceResults)

        teamResults.forEachIndexed { index, result ->
            println(result.format(index))
        }
    }

    /**
     * Extension function on List<Team> to generate TeamResults and sort them by total time
     */
    private fun List<Team>.toSortedTeamResults(raceResults: List<Result>) =
        map { team ->
            val teamTime =
                raceResults
                    .filter { it.team == team }
                    .sumOf { it.totalLapTime }
            TeamResult(team, teamTime)
        }.sortedBy { it.totalTime }

    /**
     *  Extension function on TeamResult
     *  to print the result in the desired format
     */
    private fun TeamResult.format(index: Int): String {
        val teamPosition = "${index + 1}. Team ${team.name}"
        val teamTime = "with total time $totalTime minutes"
        val sponsor =
            team.mainSponsor?.let { "Sponsored by ${it.name}" }
                ?: "No main sponsor"
        return "$teamPosition $teamTime. $sponsor"
    }

    data class TeamResult(
        val team: Team,
        val totalTime: Double,
    )

    companion object {
        // 5 minutes
        const val PITSTOP_TIME = 5.0

        // 1 minute
        const val SLOWDOWN_TIME = 1.0
    }
}

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
                it < breakdownPercent -> RaceEvent.BREAKDOWN
                it < totalExceptionPercent -> RaceEvent.COLLISION
                else -> RaceEvent.NORMAL
            }
        }
    return event
}
