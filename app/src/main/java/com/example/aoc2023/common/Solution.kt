package com.example.aoc2023.common

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import kotlin.coroutines.CoroutineContext

/**
 * A Solution. Days should inherit this.
 * Days must override [oneAndTwoAreDependant] if they are.
 */
abstract class Solution(val day: Int, private val testInput: String? = null): CoroutineScope  {
    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main // Or another dispatcher as needed


    open val oneAndTwoAreDependant = false

    protected suspend fun input(context: Context): String =
        testInput ?: readAssetFile(context, "input$day.txt")

    abstract suspend fun answer1(context: Context): Any?
    abstract suspend fun answer2(context: Context): Any?

    //Caching for activity reconfiguration
    private var cachedAnswer1: String? = null
    private var cachedAnswer2: String? = null

    fun answers(context: Context): Flow<SolutionData>{
        val resultsChannel = Channel<SolutionData>()

        when{
            cachedAnswer1 != null && cachedAnswer2 != null -> launch {
                resultsChannel.send(SolutionData(day, cachedAnswer1!!, cachedAnswer2!!))
            }
            oneAndTwoAreDependant -> launch {
                resultsChannel.send(placeholderData)

                cachedAnswer1 = answer1(context).toString()
                resultsChannel.send(SolutionData(id = day, result1 = cachedAnswer1!!))

                cachedAnswer2 = answer2(context).toString()
                resultsChannel.send(SolutionData(id = day, result1 = cachedAnswer1!!, result2 = cachedAnswer2!!))
            }
            // this is what would normally happen: Independent and not cached:
            else -> {
                launch {
                    cachedAnswer1 = answer1(context).toString()
                    resultsChannel.send(SolutionData(id = day, result1 = cachedAnswer1 ?: NOT_FOUND_YET, result2 = cachedAnswer2 ?: NOT_FOUND_YET))
                }

                launch {
                    cachedAnswer2 = answer2(context).toString()
                    resultsChannel.send(SolutionData(id = day, result1 = cachedAnswer1 ?: NOT_FOUND_YET, result2 = cachedAnswer2 ?: NOT_FOUND_YET))
                }
            }
        }

        return flow {
            for (result in resultsChannel) {
                emit(result)
            }
        }
    }

    /**
     *  Placeholder data, to be replaced by emits from [answers]
    */

    val placeholderData get() = SolutionData(id = day)

    private suspend fun readAssetFile(context: Context, fileName: String): String = withContext(Dispatchers.IO) {
        try {
            context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (e: IOException) {
            "NO INPUT DATA FOR FILE ${fileName}\n${e.stackTrace}" // Return empty string or handle exception as needed
        }
    }

    data class SolutionData(val id: Int, val result1: String = NOT_FOUND_YET, val result2: String = NOT_FOUND_YET)

    companion object{
        private const val NOT_FOUND_YET = "Not found yet"
    }
}