package nl.joozd.aoc2023.days.day7

import nl.joozd.aoc2023.common.Solution

/**
 * https://adventofcode.com/2023/day/7
 */
class Day7: Solution(7) {
    override val name = "Camel Cards"
    //Make list of hands, sort it, multiply place in List with its bid value, add them together
    override suspend fun answer1(input: String) =
        getWinnings(input.lines().map { Hand(it) }.sorted())

    override suspend fun answer2(input: String) =
        getWinnings(input.lines().map { Hand(it, useJokers = true) }.sorted())

    private fun getWinnings(hands: List<Hand>) = hands.foldIndexed(0L) { index, acc, hand ->
        acc + hand.bid * (index + 1)
    }
}