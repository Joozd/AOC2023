package nl.joozd.aoc2023.days.day5

import nl.joozd.aoc2023.common.splitToLongs

/**
 * Maps ranges to different ranges
 */
class Mapper private constructor(mapLines: List<String>) {
    constructor(map: String): this(map.lines())

    /**
     * Parent, to be filled after all have been created. For reverse tracking.
     */
    var parent: Mapper? = null

    private val ranges = generateRanges(mapLines.drop(1))

    operator fun get(valueIn: Long): Long =
        valueIn + (ranges.firstOrNull { valueIn in it.first }?.second ?: 0L)

    fun getReverse(valueOut: Long): Long{
        val originRange = ranges.firstOrNull { valueOut - it.second in it.first }
        return valueOut - (originRange?.second ?: 0)
    }

    /**
     * Returns the next Long for which the sequence changes when looking up reverse values
     * e.g. when a series would be 1-> 11, 2 -> 12, 3 -> 13, 4 -> 25, 6 -> 26,
     * this would return 4 for any of the values 1..3
     * @param current: The value to check
     * @param eventualTargetRanges: The final set of ranges that we need to match. NOTE: Make sure this is sorted!
     */
    fun nextChange(current: Long, eventualTargetRanges: List<LongRange>): Long{
        val ownNextChange = ownNextChange(current) ?: Long.MAX_VALUE

        val ourOffset = current - getReverse(current) // difference between our input and our output

        val parentNextChange = parent?.let {
            it.nextChange(getReverse(current), eventualTargetRanges) + ourOffset
        }
            ?: eventualTargetRanges.firstOrNull{ getReverse(current) in it }?.first
            ?: Long.MAX_VALUE // make sure eventualTargetRanges is sorted before we use it here!

        return minOf(ownNextChange, parentNextChange)
    }

    private fun ownNextChange(current: Long): Long?{
        val currentRange = ranges.firstOrNull{ current - it.second in it.first}
        return if (currentRange == null)
            // return first range that starts with a higher value than current
            ranges.firstOrNull { it.first.first > current }?.first?.first
        else
            //return end of this range. I'd think this would need a +1 but it doesn't. I'll think about that later.
            currentRange.first.last + currentRange.second + 1
    }

    /**
     * Generates ranges with their offsets
     */
    private fun generateRanges(map: List<String>): List<Pair<LongRange, Long>> =
        map.map{generateRange(it)}.sortedBy { it.first.first }

    private fun generateRange(line: String): Pair<LongRange, Long>{
        val numbers = line.splitToLongs()
        val start = numbers[1]
        val offset = numbers[0] - numbers[1]
        val end = start + numbers[2]

        return start..< end to offset
    }
}