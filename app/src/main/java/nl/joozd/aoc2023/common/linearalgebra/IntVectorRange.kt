package nl.joozd.aoc2023.common.linearalgebra

/**
 * This counts all values that are inside min and max values in every dimension as "in" and the rest as "out"
 * Using vectors of different dimensions is undefined and will probably cause errors.
 */
class IntVectorRange(start: IntVector, endInclusive: IntVector) : ClosedRange<IntVector> {
    //This is done so smallest values always come before larger, so comparisons work.
    override val start: IntVector =
        IntVector(*IntArray(start.size) { minOf(start[it], endInclusive[it]) })
    override val endInclusive: IntVector =
        IntVector(*IntArray(start.size) { maxOf(start[it], endInclusive[it]) })

    override fun contains(value: IntVector): Boolean =
        value.size == start.size
                && value.indices.all{ value[it] in start[it].. endInclusive[it] }
}