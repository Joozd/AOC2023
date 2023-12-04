package nl.joozd.aoc2023.common.linearalgebra

/**
 * For performance, use a HashSet for [otherPositionVectors] if it is a large list.
 */
fun IntVector.hasNeighboursIn(otherPositionVectors: Collection<IntVector>): Boolean =
    listOf(
        IntVector.NW,
        IntVector.NORTH,
        IntVector.NE,

        IntVector.WEST,
        IntVector.EAST,

        IntVector.SW,
        IntVector.SOUTH,
        IntVector.SE).any {
            this + it in otherPositionVectors
}

// Only works with a 2-dimensional vector
// Null if outside.
operator fun List<String>.get(gridLocation: IntVector): Char? =
    getOrNull(gridLocation[1])?.getOrNull(gridLocation[0])


