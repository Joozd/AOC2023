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

        assert(IntVector(3,3) in bottomRight..topLeft)
        assert(IntVector(1,1) !in bottomRight..topLeft)

        val left = IntVector(0,1)
        val right = IntVector(0,20)
        assert(IntVector(0,10) in left..right)
        assert(IntVector(10,0) !in left..right)

        assert(left in left..left)
        assert (right !in left..left)
    }

    @Test
    fun testOverlap(){
        val base = IntVector(5,5).. IntVector(10,10)
        val inside = IntVector(6,6)..IntVector(7,8) // inside
        val outside = IntVector(5,11)..IntVector(10,15) // outside
        val partial = IntVector(4,4)..IntVector(200,200) // partial overlap
        val partialOneAxis = IntVector(6,6)..IntVector(7,20)

        assert(inside.overlaps(base))
        assert(!outside.overlaps(base))
        assert(partial.overlaps(base))
        assert(partialOneAxis.overlaps(base))


    }
}