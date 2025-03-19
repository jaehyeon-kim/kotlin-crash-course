package me.jaehyeon.f1app.participants

import java.util.UUID

class Driver(
    val name: String,
    var points: Int = 0,
    val uuid: UUID = UUID.randomUUID(),
) {
    fun addPoints(newPoints: Int) {
        points += newPoints
    }
}
