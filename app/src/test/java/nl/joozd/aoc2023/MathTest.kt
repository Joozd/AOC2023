package nl.joozd.aoc2023

import nl.joozd.aoc2023.common.findCommonRotations
import nl.joozd.aoc2023.common.lcm
import org.junit.Assert.assertEquals
import org.junit.Test

class MathTest {
    @Test
    fun testLCM(){
        val test1 = listOf<Long>(3,6,9,99) to 3L
        val test2 = listOf<Long>(110, 100) to 10L
        val test3 = listOf<Long>(100, 110) to 10L
        val test4 = listOf<Long>(31, 7) to 1L
        val test5 = listOf<Long>(12, 18, 6666) to 6L

        assertEquals(test1.second, test1.first.lcm())
        assertEquals(test2.second, test2.first.lcm())
        assertEquals(test3.second, test3.first.lcm())
        assertEquals(test4.second, test4.first.lcm())
        assertEquals(test5.second, test5.first.lcm())
    }

    @Test
    fun testFindrotations(){
        val test1 = listOf<Long>(1,2,7,14) to 14L

        assertEquals(test1.second, test1.first.findCommonRotations())
    }
}