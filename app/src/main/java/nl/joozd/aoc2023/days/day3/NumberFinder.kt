package nl.joozd.aoc2023.days.day3

import nl.joozd.aoc2023.common.linearalgebra.IntVector

class NumberFinder(input: String) {
    val numbers = buildList{
        input.lines().forEachIndexed{ y, line ->
            val iterator = line.iterator()
            var x = 0
            var currentNumber = 0
            while(iterator.hasNext()){
                val c = iterator.next()
                if(c.isDigit()){
                    currentNumber *= 10
                    currentNumber += c.digitToInt()
                }
                else if(currentNumber != 0){
                    val length = currentNumber.toString().length
                    add(Number(currentNumber, IntVector(x - length, y), length))
                    currentNumber = 0
                }
                x++
            }
            if(currentNumber != 0) {
                val length = currentNumber.toString().length
                add(Number(currentNumber, IntVector(x - length, y), length))
            }
        }
    }

    data class Number(val value: Int, val startPosition: IntVector, val length: Int){
        val span = startPosition..(startPosition + IntVector(length-1, 0))
    }
}