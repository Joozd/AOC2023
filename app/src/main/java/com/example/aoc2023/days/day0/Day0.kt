package com.example.aoc2023.days.day0

import android.content.Context
import com.example.aoc2023.common.Solution

class Day0: Solution(0) {
    override suspend fun answer1(context: Context) = input(context)

    override suspend fun answer2(context: Context) = input(context).lines().size
}