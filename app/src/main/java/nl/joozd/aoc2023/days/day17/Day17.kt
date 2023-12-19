package nl.joozd.aoc2023.days.day17

import nl.joozd.aoc2023.common.Solution
class Day17: Solution(17) {
    override val name = "Clumsy Crucible"
    override suspend fun answer1(input: String): Int {
        val map = inputToMap(input)

        val routeFinder = CrucibleRouteFinder(map)
        return routeFinder.getDistance()
    }

    override suspend fun answer2(input: String): Int {
        val map = inputToMap(input)

        val routeFinder = CrucibleRouteFinder(map, isUltra = true)
        return routeFinder.getDistance()
    }

    private fun inputToMap(input: String) = input.lines().map {
        it.map { c -> c.digitToInt() }.toIntArray()
    }.toTypedArray()
}