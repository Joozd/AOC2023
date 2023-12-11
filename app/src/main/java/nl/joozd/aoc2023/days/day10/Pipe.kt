package nl.joozd.aoc2023.days.day10

import nl.joozd.aoc2023.common.linearalgebra.IntVector

// originalMap is needed to determine neigbours of S
class Pipe(coordinates: IntArray, content: Char, originalMap: List<String>): IntVector(*coordinates) {
    val neighbours: List<IntVector> =
        makeNeighbours(content, originalMap)

    fun neighbours() =
        neighbours.map { this + it }

    val isStart = content == 'S'

    var leftRightOrPipe = LeftRightOrPipe.UNKNOWN

    private fun makeNeighbours(content: Char, originalMap: List<String>): List<IntVector> = when(content){
        '|' -> northSouth
        '-' -> eastWest
        'L' -> northEast
        'J' -> northWest
        '7' -> southWest
        'F' -> southEast
        '.' -> emptyList()
        'S' -> getStartNeighbours(originalMap)
        else -> error ("Bad content \'$content\'")
    }

    private fun getStartNeighbours(originalMap: List<String>): List<IntVector>{
        return listOfNotNull(
            // Yeah I know, probably not the most understandable way.
            // For every 4 corners, check if the neighbour connects to this, if so, add that direction to the list.
            (this + NORTH).takeIf { (it.itemInMap(originalMap) ?: 'X') in "|7F" } ?.let { NORTH },
            (this + EAST).takeIf { (it.itemInMap(originalMap) ?: 'X') in "-7J" } ?.let { EAST },
            (this + SOUTH).takeIf { (it.itemInMap(originalMap) ?: 'X') in "|LJ" } ?.let { SOUTH },
            (this + WEST).takeIf { (it.itemInMap(originalMap) ?: 'X') in "-LF" } ?.let { WEST}
        )
    }

    private fun neighbourDirections() = neighbours.map{
        when {
            it == NORTH -> "N"
            it == SOUTH -> "S"
            it == EAST -> "E"
            it == WEST -> "W"
            else -> "BAD DIRECTION"
        }
    }

    companion object {
        val northSouth = listOf(NORTH, SOUTH)
        val eastWest = listOf(EAST, WEST)
        val northEast = listOf(NORTH, EAST)
        val northWest = listOf(NORTH, WEST)
        val southWest = listOf(SOUTH, WEST)
        val southEast = listOf(SOUTH, EAST)
    }


    override fun toString() = "Pipe ${this.vector.toList()} with neighbours ${neighbourDirections()} - $leftRightOrPipe" +
            if(isStart) " - START" else ""

}