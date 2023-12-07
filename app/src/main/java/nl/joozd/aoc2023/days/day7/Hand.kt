package nl.joozd.aoc2023.days.day7

class Hand private constructor(inputs: List<String>, useJokers: Boolean): Comparable<Hand> {
    constructor(input: String, useJokers: Boolean = false): this(input.split(' '), useJokers)

    val bid = inputs.last().toInt()

    private val cards = inputs.first().map { it.cardToInt(useJokers)}
    private val type = getHandType(cards, useJokers)

    /**
     * Compares this object with the specified object for order. Returns zero if this object is equal
     * to the specified [other] object, a negative number if it's less than [other], or a positive number
     * if it's greater than [other].
     */
    override fun compareTo(other: Hand): Int {
        val typeCompare = type - other.type
        if (typeCompare != 0) return typeCompare

        return compareCards(other)
    }

    private fun getHandType(hand: List<Int>, useJokers: Boolean): Int {
        if(useJokers) return getHandTypeWithJokers(hand)
        val grouping = hand.groupingBy { it }.eachCount()
        return when{
            grouping.keys.size == 1 -> FIVE_OF_A_KIND       // Only 1 type of card means 5 of a kind
            grouping.values.any { it == 4 } -> FOUR_OF_A_KIND
            grouping.values.size == 2 -> FULL_HOUSE         // if there are only 2 types of cards, and none of them is 4 cards, its a full house
            grouping.values.any { it == 3} -> THREE_OF_A_KIND
            grouping.keys.size == 3 -> TWO_PAIR             // 3 different types of cards, at most 2 per type means 2 pair
            grouping.values.any { it == 2 } -> ONE_PAIR
            else -> HIGH_CARD
        }
    }

    private fun getHandTypeWithJokers(hand: List<Int>): Int {
        val grouping = hand.groupingBy { it }.eachCount()
        val noJokers = grouping.filter { it.key != 1 }.map { it.value }
        val jokers = grouping[1] ?: 0
        if (jokers == 5) return FIVE_OF_A_KIND // get that out of the way to make sure noJokers is never empty
        return when{
            noJokers.size == 1 -> FIVE_OF_A_KIND
            noJokers.max() + jokers == 4 -> FOUR_OF_A_KIND
            noJokers.size == 2 -> FULL_HOUSE // if there are only 2 types of cards, and none of them is 4 cards, its a full house
            noJokers.max() + jokers == 3 -> THREE_OF_A_KIND
            noJokers.size == 3 -> TWO_PAIR // 3 different cards means: no jokers and 2+2+1. Any jokers would mean 3 of a kind.
            noJokers.max() + jokers == 2 -> ONE_PAIR
            else -> HIGH_CARD // no jokers, 5 different cards
        }
    }

    private fun compareCards(other: Hand): Int{
        cards.indices.forEach {
            val diff = cards[it] - other.cards[it]
            if(diff != 0) return diff
        }
        error ("No exact same hands allowed! (${this.cards} / ${other.cards}")
    }

    private fun Char.cardToInt(useJokers: Boolean) =
        if(isDigit()) digitToInt()
        else
            when(this){
                'T' -> 10
                'J' -> if (useJokers) 1 else 11
                'Q' -> 12
                'K' -> 13
                'A' -> 14
                else -> error ( "Bad card $this")
            }

    companion object {
        private const val HIGH_CARD = 0
        private const val ONE_PAIR = 1
        private const val TWO_PAIR = 2
        private const val THREE_OF_A_KIND = 3
        private const val FULL_HOUSE = 4
        private const val FOUR_OF_A_KIND = 5
        private const val FIVE_OF_A_KIND = 6
    }
}