package nl.joozd.aoc2023.days.day1

import nl.joozd.aoc2023.common.Solution

/**
 * https://adventofcode.com/2023/day/1
 */
class Day1: Solution(1) {


    override fun answer1(input: String) = input.lines()
        .sumOf{ line ->
            line.calibrationValue()
        }

    override fun answer2(input: String) = input.lines()
        .map{ it.numberify() }
        .sumOf{ line ->
            line.calibrationValue()
        }


    /**
     * Gets first and last digit and turn that into a 2-digit Int
     */
    private fun String.calibrationValue() =
        "${first{ it.isDigit() }}${last{ it.isDigit() }}".toInt()

    /**
     * Gets written-out numbers and replaces them with its value surrounded by two times itself
     * so overlapping characters will keep overlapping
     */
    private fun String.numberify(): String =
        replace("one", "one1one")
            .replace("two", "two2two")
            .replace("three", "three3three")
            .replace("four", "four4four")
            .replace("five", "five5five")
            .replace("six", "six6six")
            .replace("seven", "seven7seven")
            .replace("eight", "eight8eight")
            .replace("nine", "nine9nine")
}