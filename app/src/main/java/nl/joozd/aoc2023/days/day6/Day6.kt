package nl.joozd.aoc2023.days.day6

import nl.joozd.aoc2023.common.Solution
import nl.joozd.aoc2023.common.splitToLongs

/**
 * https://adventofcode.com/2023/day/6
 *
 * Example input:
 * Time:      7  15   30
 * Distance:  9  40  200
 */
class Day6: Solution(6) {
    /**
     * First line of inputs is race durations,
     * second line is required distance.
     * How many durations can we find for which the distance is exceeded?
     * Only integers.
     */

    override fun answer1(input: String): Long{
        val numbers = grabNumbers(input)

        // Pairs of duration to distance
        val races = numbers[0].indices.map{ numbers[0][it] to numbers[1][it] }

        val validSolitions = races.map {
            validSolutions(minPushLength(it), it).also { println(" - $it")}
        }

        // The answer is the all solutions multiplied by each other
        return validSolitions.reduce { acc, solutions -> acc * solutions }
    }

    /**
     * Remove all spaces and take it as one single race.
     */
    override fun answer2(input: String): Long{
        val numbers = grabNumbers(input).map { it.joinToString("").toLong()}
        val race = numbers[0] to numbers[1]

        return validSolutions(minPushLength(race), race).also { println(" - $it")}
    }

    private fun grabNumbers(input: String) = input.lines().map { it.drop(10).splitToLongs() }

    /**
     * Calculate the minimum length of a rectangle with perimeter (2* race.first) to get a surface of race.second or greater
     * Will throw an exception if no valid solutions (I trust the input)
     */
    private fun minPushLength(race: Pair<Long, Long>): Long{
        val perimeter = 2*race.first
        val distance = race.second

        val minLength: Double = perimeter/4.0 - kotlin.math.sqrt(perimeter * perimeter - 16.0 * distance) / 4


        println("For a square with p=$perimeter and a = $distance, min length = $minLength")

        return minLength.toLong()
    }

    /**
     * Calculates the number of valid solutions, given a minimum duration and a race.
     * A race is a pair of `time` to `distance to exceed`.
     */
    private fun validSolutions(minDuration: Long, race: Pair<Long, Long>) =
        // minDuration is rounded down. An exact match will also not beat the record, so we can always go to the next higher integer.

        // We add one because there are n+1 possibilities in n time (0 is also a possibility).
        // Since we calculate the number of failed attempts, we need to include that 0 as well.
        race.first - 2 * (minDuration + 1) + 1
}