package nl.joozd.aoc2023.days.day15

import nl.joozd.aoc2023.common.Solution


/**
 * Maybe went a little overboard with this :)
 */
class Day15: Solution(15) {
    override val name = "Lens Library"
    override fun answer1(input: String) =
        input.split(",").sumOf{ hash(it) }

    override fun answer2(input: String): Int {
        val hm = AocHashMap()
        input.split(',').map { Step(it) }.forEach {step ->
            if(step.command == Step.REMOVE)
                hm.remove(step.name)
            else
                hm[step.name] = step.value!! // we want this to throw an error if it is null because our code would be wrong
        }

        return hm.focusingPower()
    }

    private fun hash(s: String): Int =
        s.fold(0) { acc, c ->
            (acc + c.code) * 17 % 256
        }

    class Step(s: String){
        val name = s.take(s.indexOfFirst { it in "-=" } ) // all chars up to - or =
        val command = if (s[name.length] == '-') REMOVE else ADD // first char after 'name'
        val value = if (command == REMOVE) null else s.last().digitToInt()

        companion object{
            const val ADD = 0
            const val REMOVE = 1
        }
    }
}