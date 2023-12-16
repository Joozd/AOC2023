package nl.joozd.aoc2023.days.day16

import nl.joozd.aoc2023.common.linearalgebra.IntVector
import nl.joozd.aoc2023.common.linearalgebra.Matrices
import nl.joozd.aoc2023.common.linearalgebra.get
import java.util.LinkedList

class Beam(pos: IntVector, direction: IntVector, private val map: List<String>, val id: Int = highestGivenID++) {
    private var currentPos = pos
    private var currentDirection = direction

    private val isHorizontal get() = currentDirection[0] != 0// moving to 1 or -1, on x axis, needed for movement.

    /**
     * Moves this beam forward.
     * Will reflect on mirrors, split on splits
     * Will add all its visited positions to [visitedList]
     * Will keep moving until it runs off the [map], runs into a loop or follows a path another beam already followed.
     * Will spawn new beams on splitters.
     */
    fun move(visitedList: MutableMap<IntVector, MutableList<IntVector>>){
        var currentPositionValue = map[currentPos]
        while(currentPositionValue != null){
            val previousBeamsAtThisLocation = visitedList.getOrPut(currentPos) { LinkedList() }

            // check for loops and duplicates
            if (currentDirection in previousBeamsAtThisLocation) return
            previousBeamsAtThisLocation.add(currentDirection)

            when(currentPositionValue){
                // '.' -> do nothing
                '\\' -> currentDirection = if (isHorizontal) currentDirection * Matrices.TURN_RIGHT_ASCII_MAP else currentDirection * Matrices.TURN_LEFT_ASCII_MAP
                '/' -> currentDirection = if (isHorizontal) currentDirection * Matrices.TURN_LEFT_ASCII_MAP else currentDirection * Matrices.TURN_RIGHT_ASCII_MAP
                '|' -> if (isHorizontal) { // spawn another going up, we go down. If vertical, do nothing.
                    Beam(currentPos, IntVector.NORTH, map).move(visitedList)
                    currentDirection = IntVector.SOUTH
                }
                '-' -> if (!isHorizontal) { // spawn another going west, we go east. If vertical, do nothing.
                    Beam(currentPos, IntVector.WEST, map).move(visitedList)
                    currentDirection = IntVector.EAST
                }
            }

            currentPos += currentDirection
            currentPositionValue = map[currentPos]
        }
    }

    override fun toString() = "Beam $id at $currentPos -> $currentDirection (${map[currentPos]})"

    companion object{
        private var highestGivenID = 0
    }
}