package nl.joozd.aoc2023.days.day10

import nl.joozd.aoc2023.common.Solution
import nl.joozd.aoc2023.common.addCharactersAroundThisMap
import nl.joozd.aoc2023.common.linearalgebra.IntVector
import nl.joozd.aoc2023.common.linearalgebra.Matrices
import nl.joozd.aoc2023.common.linearalgebra.fourPotentialNeighbours
// import nl.joozd.aoc2023.common.toAsciiMap

class Day10: Solution(10) {
    override val name = "Pipe Maze"
    override suspend fun answer1(input: String): Any {
        val pipesMap = makePipesMap(input.lines())
        val start = pipesMap.values.first { it.isStart }

        var count = 0
        var current = setOf<IntVector>(start)
        val visited = HashSet<IntVector>()
        while(true){
            count++
            val next = current.map { neighbourVector -> pipesMap[neighbourVector]!!
                .neighbours()
                .filter { it !in visited} }
                .flatten().toSet()
            if(next.size == 1) return count // both nodes met up!
            current.forEach{
                visited.add(it)
            }
            current = next
        }
    }

    // 425, 430 too low
    override suspend fun answer2(input: String): Any{
        // add empty ground around this map to add area's of which we are sure they are "outside".
        // We only count "inside" so that is OK
        val linesWithExtraEmptyGround = input.lines().addCharactersAroundThisMap()
        val pipesMap = makePipesMap(linesWithExtraEmptyGround)
        val start = pipesMap.values.first { it.isStart }
        var current: Pipe? = start
        val visited = HashSet<IntVector>()

        while(current != null){
            current.leftRightOrPipe = LeftRightOrPipe.PIPE
            val next = pipesMap[current.neighbours().firstOrNull { it !in visited } ?: break]!! // if next is null, that means we are full circle
            val direction = next - current // subtracting vectors gives the vector from one to the other
            val left = pipesMap[next + (direction * Matrices.TURN_LEFT_ASCII_MAP)]!!    // we added empty ground so never null
            val right = pipesMap[next + (direction * Matrices.TURN_RIGHT_ASCII_MAP)]!!  // we added empty ground so never null



            val prevLeft = pipesMap[left + direction.inverse()]!!
            val prevRight = pipesMap[right + direction.inverse()]!!

            if(left.leftRightOrPipe != LeftRightOrPipe.PIPE) left.leftRightOrPipe = LeftRightOrPipe.LEFT
            if(prevLeft.leftRightOrPipe != LeftRightOrPipe.PIPE) prevLeft.leftRightOrPipe = LeftRightOrPipe.LEFT

            if(right.leftRightOrPipe != LeftRightOrPipe.PIPE) right.leftRightOrPipe = LeftRightOrPipe.RIGHT
            if(prevRight.leftRightOrPipe != LeftRightOrPipe.PIPE) prevRight.leftRightOrPipe = LeftRightOrPipe.RIGHT

            visited.add(next)
            current = next
        }
        /**
         * Go around the loop. Mark tiles as LEFT or RIGHT or PIPE.
         */

        val outsideSide = findOutside(pipesMap)
        val inside = if (outsideSide == LeftRightOrPipe.RIGHT) LeftRightOrPipe.LEFT else LeftRightOrPipe.RIGHT
        val insideFloodSources = pipesMap.values.filter { it.leftRightOrPipe == inside}.toHashSet()
        while(insideFloodSources.isNotEmpty()){
            insideFloodSources.toList().forEach {pipe ->
                val neighoursToFlood = pipe.fourPotentialNeighbours().map { pipesMap[it]!!}.filter { it.leftRightOrPipe == LeftRightOrPipe.UNKNOWN}
                neighoursToFlood.forEach {
                    it.leftRightOrPipe = inside
                    insideFloodSources.add(it)
                }
                insideFloodSources.remove(pipe)
            }
        }
/*
 * This part is for when I want to look at the pretty pipes map.
        pipesMap[IntVector(0,0)]!!.leftRightOrPipe = outsideSide

        val outsideFloodSources = pipesMap.values.filter { it.leftRightOrPipe == outsideSide}.toHashSet()
        while(outsideFloodSources.isNotEmpty()) {
            outsideFloodSources.toList().forEach { pipe ->
                val neighoursToFlood = pipe.fourPotentialNeighbours().mapNotNull { pipesMap[it] }.filter { it.leftRightOrPipe == LeftRightOrPipe.UNKNOWN }
                neighoursToFlood.forEach {
                    it.leftRightOrPipe = outsideSide
                    outsideFloodSources.add(it)
                }
                outsideFloodSources.remove(pipe)
            }
        }

        println(pipesMap.keys.toAsciiMap {
            when(pipesMap[it]?.leftRightOrPipe){
                LeftRightOrPipe.UNKNOWN -> '?'
                inside -> 'I'
                outsideSide -> '.'
                LeftRightOrPipe.PIPE -> '#'
                null -> '0'
                else -> 'X'
            }
        })

 */

        return pipesMap.values.count{ it.leftRightOrPipe == inside}
    }

    /**
     * Returns whether outside is LEFT, RIGHT or PIPE
     * This assumes map has been expanded with empty ground.
     */
    private fun findOutside(pipesMap: Map<IntVector, Pipe>): LeftRightOrPipe{
        var currentPos = pipesMap[IntVector(1,0)]!!
        while(currentPos.leftRightOrPipe == LeftRightOrPipe.UNKNOWN){
            //go down and right until we hit a LEFT or RIGHT block
            currentPos = pipesMap[currentPos + IntVector.EAST]!!
            if(currentPos.leftRightOrPipe != LeftRightOrPipe.UNKNOWN) return currentPos.leftRightOrPipe
            currentPos = pipesMap[currentPos + IntVector.SOUTH]!!
            if(currentPos.leftRightOrPipe != LeftRightOrPipe.UNKNOWN) return currentPos.leftRightOrPipe
        }
        error ("No pipe neighbours found")
    }


    private fun makePipesMap(lines: List<String>): Map<IntVector, Pipe> =
        buildList{
            lines.forEachIndexed { y, line ->
                line.forEachIndexed { x, c ->
                    add(Pipe(intArrayOf(x,y), c, lines))
                }
            }
        }.associateBy { it }
}