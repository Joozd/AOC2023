package nl.joozd.aoc2023.common.linearalgebra

/**
 * This counts all values that are inside min and max values in every dimension as "in" and the rest as "out"
 */
class IntVectorRange(override val endInclusive: IntVector, override val start: IntVector) : ClosedRange<IntVector> {
    override fun contains(value: IntVector): Boolean =
        value.size == start.size
                && value.indices.all{
                    value[it] in minOf(start[it],endInclusive[it]).. maxOf(start[it],endInclusive[it])
                }
}