package nl.joozd.aoc2023.days.day2

import nl.joozd.aoc2023.common.Solution

/**
 * https://adventofcode.com/2023/day/2
 */
class Day2: Solution(2) {
    override fun answer1(input: String) = input.lines().sumOf {
        possibleGameValue(it)
    }

    override fun answer2(input: String) = input.lines().sumOf{
        minCubesPower(it)
    }


    /**
     * return game ID if game is possible within limits, or 0 if not
     * Line example:
     *  Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green
     */
    private fun possibleGameValue(line: String): Int{
        val id = line.drop(5).let{
            it.take(it.indexOf(':')).toInt()
        }


        val grabs = getGrabs(line)
        return if (grabs.all { validGrab(it)}) id else 0
    }

    /**
     * Get grabs from a line:
     *
     *  Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green
     *  will return 2 grabs:
     *      6 red, 1 blue, 3 green
     *      2 blue, 1 red, 2 green
     */
    private fun getGrabs(line: String) =
        // remove ID, split on '; '
        line.drop(line.indexOf(':') + 2).split("; ")

    private fun minCubesPower(line: String): Int{
        val grabs = getGrabs(line)
        val cubes = grabs.map { cubesMap(it)}
        val minRed = cubes.maxOf { it["red"] ?: 0 }
        val minGreen = cubes.maxOf { it["green"] ?: 0 }
        val minBlue = cubes.maxOf { it["blue"] ?: 0 }

        return minRed * minGreen * minBlue
    }

    private fun validGrab(grab: String): Boolean{
        val cubes = cubesMap(grab)
        return (cubes["red"]?: 0) <= MAX_RED
                && (cubes["green"]?: 0) <= MAX_GREEN
                && (cubes["blue"]?: 0) <= MAX_BLUE
    }

    private fun cubesMap(grab: String) = grab.split(", ").map { cube ->
        cube.split(" ").let { amountAndColor ->
            amountAndColor[1] to amountAndColor[0].toInt()
        }
    }.toMap()

    companion object{
        private const val MAX_RED = 12
        private const val MAX_GREEN = 13
        private const val MAX_BLUE = 14
    }
}