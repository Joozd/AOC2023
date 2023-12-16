package nl.joozd.aoc2023.days.day11

import nl.joozd.aoc2023.common.Solution
import nl.joozd.aoc2023.common.linearalgebra.IntVector
import nl.joozd.aoc2023.common.linearalgebra.manhattanDistanceTo

/**
 * https://adventofcode.com/2023/day/11
 */
class Day11: Solution(11) {
    override val name = "Cosmic Expansion"
    override suspend fun answer1(input: String): Any {
        val expandedInput = expand(input)
        val stars = findStars(expandedInput)

        val distancesFromStars = stars.map{star ->
            stars.sumOf {star.manhattanDistanceTo(it) }
        }

        return distancesFromStars.sum() / 2


    }

    override suspend fun answer2(input: String) = -1L


    /**
     * Splits input into lines and expands it according to the puzzle description
     */
    private fun expand(input: String): List<String>{
        val lines = input.lines()
        val repeatedColumns = lines.indices.filter { column -> lines.all { it[column] == '.' } }
        val expandedLines = lines.map{ line -> expandLine(line, repeatedColumns)}
        return expandList(expandedLines)
    }

    private fun expandLine(line: String, repeatedColumns: List<Int>) = buildString {
        line.indices.forEach {
            append(line[it])
            if(it in repeatedColumns)
                append(".")
        }
    }

    private fun expandList(lines: List<String>): List<String> = buildList {
        lines.forEach{line ->
            add(line)
            if(line.all{ it == '.'})
                add(line)
        }
    }

    private fun findStars(lines: List<String>): List<IntVector> = buildList {
        lines.forEachIndexed { y, line ->
            line.forEachIndexed { x, c ->
                if (c == '#')
                    add(IntVector(x, y))
            }
        }
    }

}