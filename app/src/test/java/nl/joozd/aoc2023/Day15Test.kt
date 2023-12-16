package nl.joozd.aoc2023

import kotlinx.coroutines.runBlocking
import nl.joozd.aoc2023.days.day15.Day15
import org.junit.Assert.assertEquals
import org.junit.Test

class Day15Test {
    private val day = Day15()
    @Test
    fun testDay(){
        runBlocking {
            assertEquals(1320, day.answer1(testData))
            assertEquals(145, day.answer2(testData))
        }

    }
    private val testData = "rn=1,cm-,qp=3,cm=2,qp-,pc=4,ot=9,ab=5,pc-,pc=6,ot=7"
}