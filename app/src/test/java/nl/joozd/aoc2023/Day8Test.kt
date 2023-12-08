package nl.joozd.aoc2023

import nl.joozd.aoc2023.days.day8.Day8
import org.junit.Assert
import org.junit.Test

class Day8Test {
    @Test
    fun testDay() {
        val day = Day8()
        Assert.assertEquals(2, day.answer1(testData1))
        Assert.assertEquals(6, day.answer1(testData2))

        Assert.assertEquals(6L, day.answer2(testData3))
    }

    // 2
    private val testData1 = """RL

AAA = (BBB, CCC)
BBB = (DDD, EEE)
CCC = (ZZZ, GGG)
DDD = (DDD, DDD)
EEE = (EEE, EEE)
GGG = (GGG, GGG)
ZZZ = (ZZZ, ZZZ)"""

    // 6
    private val testData2 = """LLR

AAA = (BBB, BBB)
BBB = (AAA, ZZZ)
ZZZ = (ZZZ, ZZZ)"""

    private val testData3 = """LR

11A = (11B, XXX)
11B = (XXX, 11Z)
11Z = (11B, XXX)
22A = (22B, XXX)
22B = (22C, 22C)
22C = (22Z, 22Z)
22Z = (22B, 22B)
XXX = (XXX, XXX)"""
}