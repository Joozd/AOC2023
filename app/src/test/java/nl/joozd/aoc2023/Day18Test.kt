package nl.joozd.aoc2023

import kotlinx.coroutines.runBlocking
import nl.joozd.aoc2023.days.day18.Day18
import org.junit.Assert
import org.junit.Test

class Day18Test {
    private val day = Day18()

    @Test
    fun testDay() {
        runBlocking {
            Assert.assertEquals(62, day.answer1(testData))

            Assert.assertEquals(952408144115L, day.answer2(testData))
        }
    }
    private val testData = """R 6 (#70c710)
D 5 (#0dc571)
L 2 (#5713f0)
D 2 (#d2c081)
R 2 (#59c680)
D 2 (#411b91)
L 5 (#8ceee2)
U 2 (#caa173)
L 1 (#1b58a2)
U 2 (#caa171)
R 2 (#7807d2)
U 3 (#a77fa3)
L 2 (#015232)
U 2 (#7a21e3)"""
}