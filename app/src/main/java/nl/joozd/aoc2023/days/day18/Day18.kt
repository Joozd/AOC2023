package nl.joozd.aoc2023.days.day18

import nl.joozd.aoc2023.common.Solution
import nl.joozd.aoc2023.common.linearalgebra.IntVector
import nl.joozd.aoc2023.common.linearalgebra.fourPotentialNeighbours

class Day18: Solution(18) {
    override val name = "Lavaduct Lagoon"
    override suspend fun answer1(input: String): Int {
        val movements = input.lines().map { Movement(it) }
        var currentPos = IntVector(0,0)
        val map = HashMap<IntVector, String>()
        movements.forEach{ m ->
            repeat(m.distance){
                currentPos += m.direction
                map[currentPos] = m.color
            }
        }
        map.fill("000000")

        return map.size
    }

    override suspend fun answer2(input: String): Long{
        val movements = input.lines().map { Movement(it) }
        var currentPos = IntVector(0,0)
        var currentArea = 0L
        var currentLineLength = 0L

        //get rectangles, add (when y down) or subtract (when y up)
        movements.forEach { m->
            currentPos += m.directionFromColor * m.distanceFromColor
            currentLineLength += m.distanceFromColor.toLong()
            currentArea += currentPos[0].toLong() * (m.directionFromColor[1] * m.distanceFromColor)
        }
        currentArea += (currentLineLength/2)
        currentArea += 1 // 4 outside corners each miss 1/4 m2
        return currentArea
    }

    private fun MutableMap<IntVector, String>.fill(color: String){
        var currentPos = IntVector(1,1) // Gonna guess. If my PC freezes, this was outside.
        val neighbors = LinkedHashSet<IntVector>().apply{
            addAll(currentPos.fourPotentialNeighbours().filter { it !in keys } )
        }
        while(neighbors.isNotEmpty()){
            currentPos = neighbors.first().also{
                neighbors.remove(it)
            }
            put(currentPos, color)
            neighbors.addAll(currentPos.fourPotentialNeighbours().filter { it !in keys } )
        }
    }

    /*
     * Move from left to right, every time we get to a point where we have a piece extending, move it inward and add its surface area to total.
     */
    private fun getArea(coordinates: Collection<IntVector>): Long{
        TODO()
    }


}