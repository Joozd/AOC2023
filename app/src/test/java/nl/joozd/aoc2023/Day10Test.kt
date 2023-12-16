package nl.joozd.aoc2023

import kotlinx.coroutines.runBlocking
import nl.joozd.aoc2023.days.day10.Day10
import org.junit.Assert
import org.junit.Test

class Day10Test {
    @Test
    fun testDay() {
        val day = Day10()
        runBlocking {
            Assert.assertEquals(4, day.answer1(testData1))
            Assert.assertEquals(8, day.answer1(testData2))

            Assert.assertEquals(4, day.answer2(testData3))
            Assert.assertEquals(10, day.answer2(testData4))
        }
    }

    // 2
    private val testData1 = """-L|F7
7S-7|
L|7||
-L-J|
L|-JF"""

    private val testData2 = """7-F7-
.FJ|7
SJLL7
|F--J
LJ.LJ"""

    private val testData3 = """...........
.S-------7.
.|F-----7|.
.||.....||.
.||.....||.
.|L-7.F-J|.
.|..|.|..|.
.L--J.L--J.
..........."""

    private val testData4 = """FF7FSF7F7F7F7F7F---7
L|LJ||||||||||||F--J
FL-7LJLJ||||||LJL-77
F--JF--7||LJLJ7F7FJ-
L---JF-JLJ.||-FJLJJ7
|F|F-JF---7F7-L7L|7|
|FFJF7L7F-JF7|JL---7
7-L-JL7||F7|L7F-7F7|
L.L7LFJ|||||FJL7||LJ
L7JLJL-JLJLJL--JLJ.L"""
}