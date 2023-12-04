package nl.joozd.aoc2023

import nl.joozd.aoc2023.common.linearalgebra.IntVector
import org.junit.Test

class IntVectorRangeTest {
    @Test
    fun testIntRangeVector(){
        val topLeft = IntVector(2,2)
        val bottomRight = IntVector(4,4)
        assert(IntVector(3,3) in topLeft..bottomRight)
        assert(IntVector(1,1) !in topLeft..bottomRight)

        val left = IntVector(0,1)
        val right = IntVector(0,20)
        assert(IntVector(0,10) in left..right)
        assert(IntVector(10,0) !in left..right)

        assert(left in left..left)
        assert (right !in left..left)
    }
}