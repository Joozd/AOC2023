package com.example.aoc2023.common

import android.content.Context
import kotlinx.coroutines.flow.flow

abstract class Solution(private val day: Int)  {
    protected fun inputAsOne(input: List<String>) = input.joinToString("\n")
    private val inputLines by lazy { inputLines() }

    abstract fun answer1(input: List<String> = inputLines): Any?

    abstract fun answer2(input: List<String> = inputLines): Any?

    operator fun invoke() {
        println("Answers for day $day:\n1:\n${answer1()}\n\n2:\n${answer2()}")
    }

    private fun createFlowFromRaw(context: Context, res: Int) = flow{
        emit(context.resources.openRawResource(res)
            .reader()
            .readText()
        )
    }
}