package me.jaehyeon.model

data class Destination(
    val name: String,
    val price: Double,
    val description: String,
    val tags: Set<String> = setOf(),
) {
    private fun combine(other: Destination): Destination =
        Destination(
            name = "${this.name} & ${other.name}",
            price = (this.price + other.price) / 2,
            description = "${this.description} & ${other.description}",
            tags = this.tags union other.tags,
        )

    infix fun combineWith(other: Destination): Destination = combine(other)

    operator fun plus(other: Destination): Destination = combine(other)
}
