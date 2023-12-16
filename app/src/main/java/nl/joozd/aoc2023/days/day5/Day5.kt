package nl.joozd.aoc2023.days.day5

import nl.joozd.aoc2023.common.Solution
import nl.joozd.aoc2023.common.splitToLongs

class Day5: Solution(5) {
    override suspend fun answer1(input: String): Long{
        // lines and back to a string so we have a predictable line end
        val blocks = makeBlocks(input)

        // All mappers in this list are applied in series
        val mappersList = makeMappers(blocks)

        val seeds = getSeeds(blocks)

        return seeds.minOf{
            var current = it
            mappersList.forEach { mapper ->
                current = mapper[current]
            }
            current
        }
    }

    override suspend fun answer2(input: String): Long{
        val blocks = makeBlocks(input)
        val mappersListReversed = makeMappers(blocks).reversed()

        // assign each mapper (except the first one) a parent
        mappersListReversed.dropLast(1).forEachIndexed { index, mapper ->
            mapper.parent = mappersListReversed[index + 1]
        }
        val seedRanges = makeSeedRanges(blocks)

        var current = 0L
        var currentSeed = -1L // keep track of it so we don't have to recalculate it for every seed range

        while(seedRanges.none{ currentSeed in it } && current != Long.MAX_VALUE){
            current = mappersListReversed.first().nextChange(current, seedRanges)
            currentSeed = locationToSeed(current, mappersListReversed)
        }

        return current
    }

    private fun makeMappers(blocks: List<String>) = blocks.drop(1).map { Mapper(it) }

    /**
     * Seed pairs are ranges, first is start, second is length.
     */
    private fun makeSeedRanges(blocks: List<String>): List<LongRange> =
        buildList {
            val iterator = getSeeds(blocks).iterator()
            while (iterator.hasNext()) {
                val start = iterator.next()
                val length = iterator.next()
                add(start..< start + length)
            }
        }.sortedBy { it.first }

    private fun getSeeds(blocks: List<String>) = blocks.first().drop(7).splitToLongs()

    private fun locationToSeed(location: Long, reversedMap: List<Mapper>): Long {
        var current = location
        reversedMap.forEach { mapper ->
            current = mapper.getReverse(current)
        }
        return current
    }

    private fun makeBlocks(input: String) = input.lines().joinToString("\n").split("\n\n")
}