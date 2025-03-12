package me.jaehyeon.model

class DestinationBuilder {
    var name: String = ""
    var price: Double = 0.0
    var description: String = ""

    @Suppress("ktlint:standard:backing-property-naming")
    private var _tags = mutableSetOf<String>()

    fun tags(vararg tags: String) {
        _tags.addAll(tags)
    }

    fun build(): Destination = Destination(name, price, description, _tags)
}

fun destination(block: DestinationBuilder.() -> Unit): Destination = DestinationBuilder().apply(block).build()
