package nl.joozd.aoc2023.common

/**
 * Split a list of numbers in a String to Longs
 * eg. "123 456   789" -> [123L, 456L, 789L]
 */
fun String.splitToLongs() = split("\\s+".toRegex()).map { it.toLong() }