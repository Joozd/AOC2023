package nl.joozd.aoc2023.days.day3


import nl.joozd.aoc2023.common.Solution
import nl.joozd.aoc2023.common.linearalgebra.*


class Day3: Solution(3) {
    // 525558 too high
    // 522144 too low
    override fun answer1(input: String): Any{
        val numbers = NumberFinder(input).numbers
        val validNumbers = getValidNumbers(numbers, input.lines())

        return validNumbers.sumOf{ it.value }
    }

    override fun answer2(input: String) = -1

    private fun getValidNumbers(numbers: List<NumberFinder.Number>, map: List<String>): List<NumberFinder.Number> = buildList{
        numbers.forEach{ number ->
            val positions = (-1..number.length).map{
                IntVector(number.startPosition[0] + it, number.startPosition[1]).let{
                    listOf(it, it + IntVector.NORTH, it + IntVector.SOUTH)
                }
            }.flatten()
            val notSymbols = ('0'..'9').joinToString("") + "."
            if(positions.any{ (map[it] ?: '0') !in notSymbols})
                add(number.also{println("added $it")})
        }
    }
}