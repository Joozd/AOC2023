package nl.joozd.aoc2023.common.linearalgebra

/**
 * For performance, use a HashSet for [otherPositionVectors] if it is a large list.
 */
fun IntVector.hasNeighboursIn(otherPositionVectors: Collection<IntVector>): Boolean =
    potentialNeighbours().any { it in otherPositionVectors }

fun IntVector.hasNeighboursIn(otherPositionVectors: IntVectorRange): Boolean =
    potentialNeighbours().any { it in otherPositionVectors }

// Function to generate a list of potential neighbours
private fun IntVector.potentialNeighbours(): Sequence<IntVector> {
    return sequenceOf(
        IntVector.NW,
        IntVector.NORTH,
        IntVector.NE,

        IntVector.WEST,
        IntVector.EAST,

        IntVector.SW,
        IntVector.SOUTH,
        IntVector.SE
    ).map { this + it }
}

// Only works with a 2-dimensional vector
// Null if outside.
operator fun List<String>.get(gridLocation: IntVector): Char? =
    getOrNull(gridLocation[1])?.getOrNull(gridLocation[0])


