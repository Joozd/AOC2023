package nl.joozd.aoc2023.days.day16

import nl.joozd.aoc2023.common.Solution
import nl.joozd.aoc2023.common.linearalgebra.IntVector

class Day16: Solution(16) {
    override val name = "The Floor Will Be Lava"
    override fun answer1(input: String): Int{
        val visitedList = HashMap<IntVector, MutableList<IntVector>>()
        Beam(IntVector(0,0), IntVector.EAST, input.lines()).move(visitedList)
        return visitedList.size
    }

    override fun answer2(input: String): Int{
        val lines = input.lines()
        val startingPositionsLeft = lines.indices.map{ IntVector(0, it) }
        val startingPositionsTop = lines.first().indices.map{ IntVector(it, 0) }

        val maxX = lines.first().indices.last
        val maxY = lines.indices.last

        val startingPositionsRight = lines.indices.map { IntVector(maxX, it) }
        val startingPositionsBottom = lines.first().indices.map { IntVector(it, maxY) }

        val maxLeft = startingPositionsLeft.maxOf {
            val visitedList = HashMap<IntVector, MutableList<IntVector>>()
            Beam(it, IntVector.EAST, lines).move(visitedList)
            visitedList.size
        }
        val maxTop = startingPositionsTop.maxOf {
            val visitedList = HashMap<IntVector, MutableList<IntVector>>()
            Beam(it, IntVector.SOUTH, lines).move(visitedList)
            visitedList.size
        }
        val maxRight = startingPositionsRight.maxOf {
            val visitedList = HashMap<IntVector, MutableList<IntVector>>()
            Beam(it, IntVector.WEST, lines).move(visitedList)
            visitedList.size
        }
        val maxBottom = startingPositionsBottom.maxOf {
            val visitedList = HashMap<IntVector, MutableList<IntVector>>()
            Beam(it, IntVector.NORTH, lines).move(visitedList)
            visitedList.size
        }
        return maxOf(maxLeft, maxTop, maxRight, maxBottom)

    }
}