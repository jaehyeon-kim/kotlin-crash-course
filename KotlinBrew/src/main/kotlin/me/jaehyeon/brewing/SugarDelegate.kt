package me.jaehyeon.brewing

import kotlin.reflect.KProperty

class SugarDelegate(
    private var level: Int = 0,
) {
    operator fun getValue(
        thisRef: Any?,
        property: KProperty<*>,
    ): Int = level

    operator fun setValue(
        thisRef: Any?,
        property: KProperty<*>,
        value: Int,
    ) {
        // ensures the value is between 0 and 10
        level = value.coerceIn(0, 10)
    }
}
