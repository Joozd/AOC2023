package nl.joozd.aoc2023

import nl.joozd.aoc2023.common.linearalgebra.IntVector
import nl.joozd.aoc2023.common.linearalgebra.Matrices
import org.junit.Assert.assertEquals
import org.junit.Test

class IntVectorMatrixTest {
    @Test
    fun testTurnLeftRight(){
        assertEquals(IntVector.EAST, IntVector.SOUTH * Matrices.TURN_LEFT_ASCII_MAP)
    }
}