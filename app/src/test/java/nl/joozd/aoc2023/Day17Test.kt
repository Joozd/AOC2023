package nl.joozd.aoc2023

import kotlinx.coroutines.runBlocking
import nl.joozd.aoc2023.days.day17.Day17
import org.junit.Assert
import org.junit.Test

class Day17Test {
    private val day = Day17()
    @Test
    fun testDay(){
        runBlocking {
            Assert.assertEquals(102, day.answer1(testData))

            Assert.assertEquals(94, day.answer2(testData))
        }
    }

    private val testData = """2413432311323
3215453535623
3255245654254
3446585845452
4546657867536
1438598798454
4457876987766
3637877979653
4654967986887
4564679986453
1224686865563
2546548887735
4322674655533"""
}