package nl.joozd.aoc2023

import nl.joozd.aoc2023.days.day11.Day11
import org.junit.Assert
import org.junit.Test

class Day11Test {
    @Test
    fun testDay() {
        val day = Day11()
        Assert.assertEquals(374, day.answer1(testData))

        Assert.assertEquals(-1, day.answer2(testData))
    }

    private val testData = """...#......
.......#..
#.........
..........
......#...
.#........
.........#
..........
.......#..
#...#....."""
}