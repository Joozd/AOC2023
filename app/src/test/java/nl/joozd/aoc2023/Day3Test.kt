package nl.joozd.aoc2023

import nl.joozd.aoc2023.days.day3.Day3
import org.junit.Assert.assertEquals
import org.junit.Test


class Day3Test {
    @Test
    fun testDay3(){
        val day3 = Day3()
        assertEquals(4361, day3.answer1(testData))


    }

    private val testData = """467..114.
...*.....
..35..633
......#..
617*.....
.....+.58
..592....
......755
...${'$'}.*...
.664.598."""
}