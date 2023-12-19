package nl.joozd.aoc2023.days.day17

import nl.joozd.aoc2023.common.linearalgebra.IntVector
import nl.joozd.aoc2023.common.linearalgebra.Matrices

class VisitedPoint(val location: IntVector, val direction: IntVector, val straightMoves: Int, val totalDistance: Int, val isUltra: Boolean = false, val previousPoint: VisitedPoint?): Comparable<VisitedPoint> {
    /**
     * Do not compare totalDistance
     */
    override fun equals(other: Any?): Boolean =
        other is VisitedPoint
                && location == other.location
                && direction == other.direction
                && straightMoves == other.straightMoves

    override fun hashCode(): Int {
        var result = location.hashCode()
        result = 31 * result + direction.hashCode()
        result = 3 * result + straightMoves
        return result
    }

    override fun toString(): String = "VP: $location->$direction($straightMoves): $totalDistance"


    /**
     * Compares this object with the specified object for order. Returns zero if this object is equal
     * to the specified [other] object, a negative number if it's less than [other], or a positive number
     * if it's greater than [other].
     */
    override fun compareTo(other: VisitedPoint): Int = totalDistance - other.totalDistance

    fun nextLocations(map: Array<IntArray>): List<VisitedPoint> =
        if (isUltra)
            when{
                straightMoves < 4 -> nextStraight(map)
                straightMoves < 10 -> nextStraight(map) + nextWithTurn(map)
                else -> nextWithTurn(map)
            }

        else if (straightMoves< 3) nextWithTurn(map) + nextStraight(map)
            else nextWithTurn(map)

    private fun nextWithTurn(map: Array<IntArray>): List<VisitedPoint> =
        listOf(Matrices.TURN_RIGHT_ASCII_MAP, Matrices.TURN_LEFT_ASCII_MAP).mapNotNull{ turn ->
            val nextDirection = direction * turn
            val nextPoint = location + nextDirection
            map.getOrNull(nextPoint[1])?.getOrNull(nextPoint[0])?.let{ newDistance ->
                VisitedPoint(nextPoint, nextDirection, 1, totalDistance + newDistance, isUltra, this)
            }
        }

    private fun nextStraight(map: Array<IntArray>): List<VisitedPoint> {
        val nextPoint = location + direction
        return listOfNotNull(map.getOrNull(nextPoint[1])?.getOrNull(nextPoint[0])?.let { newDistance ->
            VisitedPoint(nextPoint, direction, straightMoves + 1, totalDistance + newDistance, isUltra, this)
        })
    }


}