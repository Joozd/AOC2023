package nl.joozd.aoc2023.days.day19

class WorkflowSolver(workflows: List<String>) {
    val workflowMap: Map<String, String> = workflows.map {workflow ->
        workflow.split( '{')
            .let{  it.first() to it.last().dropLast(1) }
    }.toMap()


    fun solve(currentRange: Ranges = Ranges(), currentNode: String = "in"): Long {
        var range = currentRange
        return workflowMap[currentNode]!!.split(',').sumOf { workFlowPart ->
            when {
                workFlowPart == "A" -> range.value()

                workFlowPart == "R" -> 0L

                ":" !in workFlowPart -> solve(range, workFlowPart)

                else -> {
                    val (constraint, target) = workFlowPart.split(':')
                    when (target) {
                        "A" -> range.withConstraint(constraint).value()

                        "R" -> 0L

                        else -> solve(range.withConstraint(constraint), target)
                    }.also {
                        range = range.withInvertedConstraint(constraint) // remaining range for rest does not include solved range
                    }
                }
            }
        }.also { println("Solved $currentNode: $it (from range $currentRange") }
    }


    data class Ranges(
        val x: IntRange = IntRange(1,4000),
        val m: IntRange = IntRange(1,4000),
        val a: IntRange = IntRange(1,4000),
        val s: IntRange = IntRange(1,4000)
    )
    {
        /**
         * Constraint must be something like
         * "a<3"or "x>100"
         */
        fun withConstraint(constraint: String): Ranges = when(constraint.take(2)){
            "x<" -> this.copy(x = this.x.first..< constraint.drop(2).toInt())
            "x>" -> this.copy(x = (constraint.drop(2).toInt() + 1)..this.x.last)

            "m<" -> this.copy(m = this.m.first..< constraint.drop(2).toInt())
            "m>" -> this.copy(m = (constraint.drop(2).toInt() + 1)..this.m.last)

            "a<" -> this.copy(a = this.a.first..< constraint.drop(2).toInt())
            "a>" -> this.copy(a = (constraint.drop(2).toInt() + 1)..this.a.last)

            "s<" -> this.copy(s = this.s.first..< constraint.drop(2).toInt())
            "s>" -> this.copy(s = (constraint.drop(2).toInt() + 1)..this.s.last)

            else -> error("Ranges.withConstraint(): BAD CONSTRAINT: $constraint")
        }

        /**
         * What is left if a constraint is not true
         */
        fun withInvertedConstraint(constraint: String): Ranges = when(constraint.take(2)){
            "x>" -> this.copy(x = this.x.first.. constraint.drop(2).toInt())
            "x<" -> this.copy(x = (constraint.drop(2).toInt())..this.x.last)

            "m>" -> this.copy(m = this.m.first.. constraint.drop(2).toInt())
            "m<" -> this.copy(m = (constraint.drop(2).toInt())..this.m.last)

            "a>" -> this.copy(a = this.a.first.. constraint.drop(2).toInt())
            "a<" -> this.copy(a = (constraint.drop(2).toInt())..this.a.last)

            "s>" -> this.copy(s = this.s.first.. constraint.drop(2).toInt())
            "s<" -> this.copy(s = (constraint.drop(2).toInt())..this.s.last)

            else -> error("Ranges.withConstraint(): BAD CONSTRAINT: $constraint")
        }
        fun value(): Long = (x.last - x.first + 1).toLong() * // 4000-1 is 3999 but has 4000 values, so we need to add 1
                (m.last - m.first + 1) *
                (a.last - a.first + 1) *
                (s.last - s.first + 1)
    }
}