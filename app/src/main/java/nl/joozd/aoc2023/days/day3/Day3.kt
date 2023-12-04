package nl.joozd.aoc2023.days.day3


import nl.joozd.aoc2023.common.Solution
import nl.joozd.aoc2023.common.linearalgebra.*
import kotlin.time.measureTime


/**
 * https://adventofcode.com/2023/day/3
 */
class Day3: Solution(3) {
    override val oneAndTwoAreDependant: Boolean = true // one caches found numbers, two uses that cache

    private lateinit var numbers: List<NumberFinder.Number>

    /**
     * Sum of all numbers that are adjacent to a symbol (any character that is not a digit or '.')
     * Includes diagonal.
     */
    override fun answer1(input: String): Any{
        val validNumbers: List<NumberFinder.Number>

        val numbersTime = measureTime {
            numbers = NumberFinder(input).numbers
        }
        val validNumbersTime = measureTime {
            validNumbers = getValidNumbers(numbers, input.lines())
        }
        println("generate numbers: $numbersTime")
        println("validNumbersTime: $validNumbersTime")

        return validNumbers.sumOf{ it.value }
    }

    override fun answer2(input: String): Any{
        val lines = input.lines()
        val possibleGears = findGears(lines)
        val gearsWithNumbersMap: Map<IntVector, List<NumberFinder.Number>>

        val gearsMapTime = measureTime {
            gearsWithNumbersMap = makeGearsWithNumbersMap(possibleGears, numbers).filter {
                it.value.size == 2
            }
        }
        println("gearsMapTime: $gearsMapTime")
        return gearsWithNumbersMap.values.sumOf{
            it[0].value * it[1].value
        }
    }

    private fun getValidNumbers(numbers: List<NumberFinder.Number>, map: List<String>): List<NumberFinder.Number> = buildList{
        numbers.forEach{ number ->
            val positions = (-1..number.length).map{
                IntVector(number.startPosition[0] + it, number.startPosition[1]).let{
                    listOf(it, it + IntVector.NORTH, it + IntVector.SOUTH)
                }
            }.flatten()
            val notSymbols = ('0'..'9').joinToString("") + "."
            if(positions.any{ (map[it] ?: '0') !in notSymbols})
                add(number)
        }
    }


    private fun findGears(lines: List<String>) = buildList<IntVector> {
        lines.forEachIndexed { y, line ->
            line.forEachIndexed { x, c ->
                if (c == '*')
                    add (IntVector(x,y))
            }
        }
    }

    private fun makeGearsWithNumbersMap(
        gears: List<IntVector>,
        numbers: List<NumberFinder.Number>
    ): Map<IntVector, List<NumberFinder.Number>> {
        return gears.associateWith { gear ->
            numbers.filter { number -> gear.hasNeighboursIn(number.span) }
        }
    }
}