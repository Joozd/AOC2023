package nl.joozd.aoc2023

import kotlinx.coroutines.runBlocking
import nl.joozd.aoc2023.days.day16.Day16
import org.junit.Assert.assertEquals
import org.junit.Test

class Day16Test {
    private val day = Day16()
    @Test
    fun testDay(){
        runBlocking {
            assertEquals(46, day.answer1(testData))

            assertEquals(51, day.answer2(testData))
        }
    }


    private val testData = """.|...\....
|.-.\.....
.....|-...
........|.
..........
.........\
..../.\\..
.-.-/..|..
.|....-|.\
..//.|...."""
}