package nl.joozd.aoc2023.days.day18

import nl.joozd.aoc2023.common.linearalgebra.IntVector

class Movement(instruction: String) {
    val direction = when(instruction.first()) {
        'L' -> IntVector.WEST
        'R' -> IntVector.EAST
        'U' -> IntVector.NORTH
        'D' -> IntVector.SOUTH
        else -> error("Wrong direction ${instruction.first()} in $instruction")
    }

    val distance: Int
    val color: String
    val distanceFromColor: Int
    val directionFromColor: IntVector

    init{
        val words = instruction.split(' ')
        distance = words[1].toInt()
        color = words.last().drop(2).dropLast(1)
        distanceFromColor = color.dropLast(1).toInt(16)
        directionFromColor = when(color.last()){
            '0' -> IntVector.EAST
            '1' -> IntVector.SOUTH
            '2' -> IntVector. WEST
            '3' -> IntVector.NORTH
            else -> error ("Bad direction in color $color")
        }
    }

    override fun toString() =
        "fromColor: $directionFromColor - $distanceFromColor"
}