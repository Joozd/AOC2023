package nl.joozd.aoc2023.common

import java.util.LinkedList

// There is probably a much more math-y way to do this
// The way I do it:
//  - Get the first two numbers.
//  - Replace the largest by largest - smallest
//  - repeat until they are both the same.
//  - Now, we have the LCM of those two numbers.
//  - Replace one of them by the next number in the iterator.
//  - repeat until iterator is empty
fun Iterable<Long>.lcm(): Long {
    val iterator = iterator()
    require(iterator.hasNext()) { "Cannot find LCM of empty collection" }
    var left = iterator.next()
    while (iterator.hasNext()) {
        var right = iterator.next()
        while (left != right) {
            if (left > right)
                left -= right
            else right -= left
        }
    }
    return left
}

/**
 * Find the lowest number that can be divided by all the items in this list
 */
// throws Exception for lists with less than 2 items
fun List<Long>.findCommonRotations(): Long{
    val ll = LinkedList(this)
    var left = ll.removeFirst()
    while(ll.isNotEmpty()){
        val right = ll.removeFirst()
        val lcm = listOf(left, right).lcm()

        left *= ( right / lcm)
    }
    return left
}