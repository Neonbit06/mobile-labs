package ru.agapov.lab1.data

import kotlin.random.Random

object ListGenerator {
    private val DEFAULT_RANGE = -50 .. 50
    private const val DEFAULT_SIZE = 20

    fun generate(
        size: Int = DEFAULT_SIZE,
        range: IntRange = DEFAULT_RANGE,
        random: Random = Random.Default
    ): List<Int> {
        return List(size) { random.nextInt(range.first, range.last + 1) }
    }
}