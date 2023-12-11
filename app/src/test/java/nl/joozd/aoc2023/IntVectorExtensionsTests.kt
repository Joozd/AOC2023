package nl.joozd.aoc2023

import nl.joozd.aoc2023.common.linearalgebra.IntVector
import nl.joozd.aoc2023.common.linearalgebra.manhattanDistanceTo
import org.junit.Assert.assertEquals
import org.junit.Test

class IntVectorExtensionsTests {
    @Test
    fun testManhattenDistance(){
        assertEquals(10, IntVector(0,0).manhattanDistanceTo(IntVector(0,10)))
        assertEquals(10, IntVector(0,0).manhattanDistanceTo(IntVector(10,0)))
        assertEquals(10, IntVector(0,0).manhattanDistanceTo(IntVector(5,5)))
        assertEquals(10, IntVector(5,5).manhattanDistanceTo(IntVector(10,10)))
        assertEquals(0, IntVector(0,0).manhattanDistanceTo(IntVector(0,0)))
    }
}