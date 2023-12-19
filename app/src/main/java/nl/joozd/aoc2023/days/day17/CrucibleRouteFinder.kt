package nl.joozd.aoc2023.days.day17

import nl.joozd.aoc2023.common.linearalgebra.IntVector
import java.util.PriorityQueue

class CrucibleRouteFinder(
    private val map: Array<IntArray>,
    startPos: IntVector = IntVector(0,0),
    startDir: IntVector = IntVector.EAST,
    endPos: IntVector? = null, // null means right bottom
    isUltra: Boolean = false,
) {
    private val endPos = endPos ?: IntVector(map.first().size -1, map.size - 1)
    private val startPos = VisitedPoint(startPos, startDir, 0, 0, isUltra, null)

    fun getDistance(): Int {
        var currentPos = startPos
        val seen = hashSetOf(startPos)
        val frontier = PriorityQueue<VisitedPoint>()
        while(currentPos.location != endPos){
            val candidates = currentPos.nextLocations(map).filter { it !in seen }.also{ cc ->
                seen.addAll(cc)
            }
            frontier.addAll(candidates)

            currentPos = frontier.poll()!!
        }
        return currentPos.totalDistance
    }
}