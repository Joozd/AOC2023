package nl.joozd.aoc2023.days.day4

import nl.joozd.aoc2023.common.Solution

class Day4: Solution(4) {
    override fun answer1(input: String): Any {
        val cardNumberLength = input.indexOf(':') + 2 // 0-indexed, followed by a whitespace
        val cards = input.lines().map { it.drop(cardNumberLength) } // get the relevant part of every card (winning and actual numbers)
        return cards.sumOf { getCardValue(it) } // drop first part of string here, so we don't have to calculate that every time (card number length is fixed)
    }


    /**
     * Keep a list of how many of each card we have.
     * We multiply the result of each card by the amount of it we have, and modify the list that keeps track of the amount of cards in place.
     */
    override fun answer2(input: String): Any {
        val cardNumberLength = input.indexOf(':') + 2 // 0-indexed, followed by a whitespace
        val cards = input.lines().map { it.drop(cardNumberLength) }

        val copies = IntArray(cards.size) { 1 } // start with 1 of each card

        cards.forEachIndexed { i, card ->
            val count = countWinningNumbers(card)
            // the next [count] cards get as many copies as we have copies of the current card.
            repeat(count){
                copies[i + 1 + it] += copies[i]
            }
        }
        return copies.sum()
    }


    private fun countWinningNumbers(line: String): Int{
        val left: List<Int>
        val right: List<Int>
        line.split(" | ").let{
            left = it[0].numbers()
            right = it[1].numbers()
        }
        return right.count { it in left }
    }

    /**
     * The answer is 1 for 1 winning card, doubled for every next winning card, or 0 for 0 winning cards.
     * We do that by starting with 1, and shifting that bit left (which is the same as doubling) as many times as we have cards.
     * Then, we shift it back right by 1, to correct for the fact that we started at 1 instead of 0.
     */
    private fun getCardValue(line: String): Int =
        1.shl(countWinningNumbers(line)).shr(1)

    private fun String.numbers(): List<Int> =
        trim().split(' ').filter { it.isNotBlank()}.map { it.toInt()}
}