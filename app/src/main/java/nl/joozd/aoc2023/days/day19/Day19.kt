package nl.joozd.aoc2023.days.day19

import nl.joozd.aoc2023.common.Solution

class Day19: Solution(19) {
    override val name = "Placeholder Stub"
    override suspend fun answer1(input: String): Int{
        val functionsLibrary = HashMap<String, WorkFlow>()
        val inputParts = input.lines().joinToString("\n").split("\n\n")
        inputParts.first().lines().forEach{
            makeWorkflow(it, functionsLibrary).let{ wf ->
                functionsLibrary[wf.first] = wf.second
            }
        }

        val items = inputParts.last().lines().map { makeItem(it) }

        return items.sumOf { functionsLibrary["in"]!!(it) }
    }

    override suspend fun answer2(input: String) = WorkflowSolver(input.split("\n\n").first().lines()).solve()


    private fun makeWorkflow(line: String, functionsLibrary: Map<String, WorkFlow>): Pair<String, WorkFlow>{
        val split = line.split('{')
        return split.first() to makeFunction(split.last().dropLast(1), functionsLibrary)
    }

    private fun makeFunction(workflow: String, functionsLibrary: Map<String, WorkFlow>): WorkFlow {
        val ruleSet = workflow.split(',')
            .map { makeWorkFlowItem(it) }
        return WorkFlow { item ->
            when (val result = ruleSet.firstNotNullOf {it(item) }){
                "A" -> getValueOfItem(item)
                "R" -> 0
                else -> functionsLibrary[result]!!(item)
            }
        }
    }

    private fun makeWorkFlowItem(ruleString: String): WorkFlowItem = when{
        ':' !in ruleString -> WorkFlowItem{ ruleString }
        '>' in ruleString -> {
            val itemType = ruleString.first()
            val rest = ruleString.drop(2).split(':')
            val result = rest.last()
            val amount = rest.first().toInt()
            WorkFlowItem{ item ->
                if (item[itemType]!! > amount) result else null
            }
        }
        else -> { // smoller than is only one left
            val itemType = ruleString.first()
            val rest = ruleString.drop(2).split(':')
            val result = rest.last()
            val amount = rest.first().toInt()
            WorkFlowItem{ item ->
                if (item[itemType]!! < amount) result else null
            }
        }
    }

    private fun interface WorkFlow{
        operator fun invoke(item: Map<Char, Int>): Int
    }

    /**
     * Null if condition not met, true if accepted, false if rejected
     */
    private fun interface WorkFlowItem{
        operator fun invoke(item: Map<Char, Int>): String?
    }

    private fun makeItem(input: String): Map<Char, Int> = buildMap{
        input.drop(1).dropLast(1).split(',').forEach { part ->
            part.split('=').let{
                put(it.first().first(), it.last().toInt())
            }
        }
    }

    private fun getValueOfItem(item: Map<Char, Int>) = item.values.sum()
}