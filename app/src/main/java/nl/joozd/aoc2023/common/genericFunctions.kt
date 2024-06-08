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

fun <T: IntVector> Collection<T>.toAsciiMap(displayFunction: (T) -> Char): String{
    val minX = minOf{ it[0] }
    val maxX = maxOf{ it[0] }
    val minY = minOf{ it[1] }
    val maxY = maxOf{ it[1] }

    val xOffset = minX * -1
    val yOffset = minY * -1

    val height = maxY - minY + 1
    val width = maxX - minX + 1

    val result = Array(height) { CharArray(width) { ' '} }


    forEach { item ->
        result[item[1] + yOffset][item[0] + xOffset] = displayFunction(item)
    }
    return  result.joinToString("\n") { it.joinToString("")}
}
