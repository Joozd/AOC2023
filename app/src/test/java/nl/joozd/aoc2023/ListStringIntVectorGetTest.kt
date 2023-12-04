package nl.joozd.aoc2023

import nl.joozd.aoc2023.common.linearalgebra.IntVector
import org.junit.Assert.assertEquals
import org.junit.Test
import nl.joozd.aoc2023.common.linearalgebra.*

class ListStringIntVectorGetTest {
    @Test
    fun test(){
        val data = listOf(
            "abc",
            "def",
            "ghi")
        val topLeft = IntVector(0,0)
        val topRight = IntVector(2, 0)
        val bottomLeft = IntVector(0,2)
        val bottomRight = IntVector(2,2)

        assertEquals(data[topLeft], 'a')
        assertEquals(data[topRight], 'c')
        assertEquals(data[bottomLeft], 'g')
        assertEquals(data[bottomRight], 'i')



    }
}