package nl.joozd.aoc2023

import kotlinx.coroutines.runBlocking
import nl.joozd.aoc2023.days.day7.Day7
import org.junit.Assert
import org.junit.Test

class Day7Test {
    @Test
    fun testDay() {
        val day = Day7()
        runBlocking {
            Assert.assertEquals(6440, day.answer1(testData))

            Assert.assertEquals(5905, day.answer2(testData))
        }
    }

    private val testData = """32T3K 765
T55J5 684
KK677 28
KTJJT 220
QQQJA 483"""
}