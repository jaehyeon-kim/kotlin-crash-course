package me.jaehyeon.me.jaehyeon.f1app

import me.jaehyeon.me.jaehyeon.f1app.participants.Driver
import me.jaehyeon.me.jaehyeon.f1app.participants.RaceCar
import me.jaehyeon.me.jaehyeon.f1app.participants.Team

enum class RaceEvent {
    NORMAL,
    BREAKDOWN,
    COLLISION,
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

    companion object {
        // 5 minutes
        const val PITSTOP_TIME = 5.0
    }
}
