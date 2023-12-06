package nl.joozd.aoc2023

import nl.joozd.aoc2023.days.day6.Day6
import org.junit.Assert
import org.junit.Test

class Day6Test {
    @Test
    fun testDay() {
        val day = Day6()
        Assert.assertEquals(288, day.answer1(testData))

        Assert.assertEquals(71503, day.answer2(testData))
        //NOTE test data mises some 1-off errors
    }

    private val testData = """Time:      7  15   30
Distance:  9  40  200"""
}