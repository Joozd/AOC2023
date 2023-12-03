package com.example.aoc2023.days.day1

import android.content.Context
import com.example.aoc2023.common.Solution

/**
 * @see https://adventofcode.com/2023/day/1
 */
class Day1: Solution(1) {


    override suspend fun answer1(context: Context) = input(context).lines()
        .sumOf{ line ->
            line.calibrationValue()
        }

    override suspend fun answer2(context: Context) = input(context).lines()
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