package me.jaehyeon.compliment

import mu.KotlinLogging

private val logger = KotlinLogging.logger { }

/**
 * This class is responsible for generating random compliments.
 * A compliment is a combination of a randomly selected adjective and subject.
 *
 */
class ComplimentGenerator {
    val adjective = listOf("Great", "Amazing", "Incredible", "Fantastic", "Awesome")
    val subjects = listOf("job", "work", "effort", "performance", "attitude")

    /**
     * Generates a random compliment by picking one adjective and one subject randomly.
     * Logs the generated compliment
     *
     * @return a string representing the generated compliment
     */
    fun generate(): String {
        val compliment = "${adjective.random()} ${subjects.random()}"
        logger.info("Generated compliment: $compliment")
        return compliment
    }
}

/**
 * The entry point of the application.
 * Creates a ComplimentGenerator instance and prints a compliment 5 times.
 */
fun main() {
    val generator = ComplimentGenerator()
    repeat(5) {
        println("Compliment: ${generator.generate()}")
    }
}
