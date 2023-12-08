package nl.joozd.aoc2023.days.day8

import nl.joozd.aoc2023.common.Solution
import nl.joozd.aoc2023.common.findCommonRotations
import java.util.LinkedList

class Day8: Solution(8) {
    override val name = "Haunted Wasteland"

    // 19631
    override fun answer1(input: String): Long{
        val lines = input.lines()
        val nodesMap: Map<String, Node> = lines.drop(2)
            .map { Node.ofLine(it) }
            .associateBy { it.name }

        var currentPosition = START
        var currentDirections = makeFullDirections(lines)
        var stepsCounted = 0L

        while(currentPosition != END){
            if (currentDirections.isEmpty())
                currentDirections = makeFullDirections(lines)
            val currentNode = nodesMap[currentPosition]!! // I trust the input, no null check
            currentPosition = if(currentDirections.removeFirst() == 'L') currentNode.left else currentNode.right
            stepsCounted++
        }
        return stepsCounted
    }

    // 21003205388413
    override fun answer2(input: String): Any {
        val lines = input.lines()
        val nodesMap: Map<String, Node> = lines.drop(2)
            .map { Node.ofLine(it) }
            .associateBy { it.name }

        val positions = nodesMap.keys.filter { it.endsWith('A')}.toTypedArray()
        val lengths = positions.map{
            var currentPosition = it
            var currentDirections = makeFullDirections(lines)
            var stepsCounted = 0L

            while(!currentPosition.endsWith('Z')){
                if (currentDirections.isEmpty())
                    currentDirections = makeFullDirections(lines)
                val currentNode = nodesMap[currentPosition]!! // I trust the input, no null check
                currentPosition = if(currentDirections.removeFirst() == 'L') currentNode.left else currentNode.right
                stepsCounted++
            }
            stepsCounted
        }

        return lengths.findCommonRotations()
    }

    private fun makeFullDirections(lines: List<String>) = LinkedList(lines.first().toList())



    data class Node(val name: String, val left: String, val right: String){
        companion object{
            fun ofLine(line: String):Node {
                val name = line.take(3)
                val parts = line.drop(7).dropLast(1).split(", ")
                return Node(name, parts[0], parts[1])
            }
        }
    }

    companion object{
        private const val START = "AAA"
        private const val END = "ZZZ"
    }
}