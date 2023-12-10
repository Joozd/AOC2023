package nl.joozd.aoc2023.common

import nl.joozd.aoc2023.common.linearalgebra.IntVector

/**
 * Split a list of numbers in a String to Longs
 * eg. "123 456   789" -> [123L, 456L, 789L]
 */
fun String.splitToLongs() = trim().split("\\s+".toRegex()).map { it.toLong() }

fun Collection<Long>.multiply(): Long = reduce { acc, solutions -> acc * solutions }

fun List<String>.addCharactersAroundThisMap(character: Char = '.'): List<String>{
    val l = first().length + 2
    val firstLastLine = CharArray(l) { character }.joinToString("")
    return listOf(firstLastLine) + this.map { ".$it."} + firstLastLine
}

fun Collection<IntVector>.toAsciiMap(displayFunction: (IntVector) -> Char): String{
    val minX = minOf{ it.first() }
    val maxX = maxOf{ it.first() }
    val minY = minOf{ it[1] }
    val maxY = maxOf{ it[1] }

    val xOffset = minX * -1
    val yOffset = minY * -1

    val height = maxY - minY
    val width = maxX - minX

    return Array(height){ yNoOffset ->
        CharArray(width){ xNoOffset ->
            displayFunction(IntVector(xNoOffset + xOffset, yNoOffset + yOffset))
        }.joinToString("")
    }.joinToString("\n")
}
