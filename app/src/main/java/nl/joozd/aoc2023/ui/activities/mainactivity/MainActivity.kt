package nl.joozd.aoc2023.ui.activities.mainactivity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import nl.joozd.aoc2023.common.Solution
import nl.joozd.aoc2023.days.days
import nl.joozd.aoc2023.ui.theme.AOC2023Theme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

class MainActivity : ComponentActivity() {
    val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AOC2023Theme {
                MyApp(modifier = Modifier.fillMaxSize())
            }
        }
    }

    @Composable
    private fun MyApp(
        modifier: Modifier = Modifier
    ) {
        LazyColumn(modifier = modifier.padding(vertical = 4.dp)) {
            items(items = days){ solution ->
                DaySolution(solution = solution)
            }
        }
    }


    @Composable
    fun DaySolution(solution: Solution, modifier: Modifier = Modifier) {
        // we use [remember] instead of [rememberSaveable] so we get to recalculate on process death.
        // Answers are cached in viewModel (on flow collection)
        var answers by remember { mutableStateOf(solution.placeholderData) }
        var calculate by remember { mutableStateOf(false) }
        var answersFlow: Flow<Solution.SolutionData> by remember { mutableStateOf(emptyFlow()) }

        val context = LocalContext.current

        LaunchedEffect(calculate){
            // Check if cached data exists, if so, display that
            val cachedAnswer = viewModel.cachedData[answers.id] ?: solution.placeholderData
            if (cachedAnswer.result1 != Solution.NOT_FOUND_YET || cachedAnswer.result2 != Solution.NOT_FOUND_YET) {
                calculate = true // removes "calculate button"
                answers = cachedAnswer
            }
            else if(calculate) {
                // This happens if calculate is presed and no data is cached
                answersFlow.collect { aa ->
                    answers = aa
                    viewModel.cachedData[answers.id] = answers
                }
            }
        }

        Surface(color = MaterialTheme.colorScheme.primary,
            modifier = modifier.padding(vertical = 4.dp, horizontal = 8.dp)) {
            Row(modifier = Modifier.padding(24.dp)) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "Day ${answers.id}", style = MaterialTheme.typography.headlineMedium)
                    Text(text = "1: ${answers.result1}")
                    Text(text = "2: ${answers.result2}")
                }
                if(!calculate)
                    ElevatedButton(onClick = {
                        answersFlow = solution.answers(context)
                        calculate = true
                    }) {
                        Text("Calculate")
                    }
            }
        }
    }

    @Preview(showBackground = true, widthDp = 360)
    @Composable
    private fun GreetingPreview() {
        AOC2023Theme {
            MyApp()
        }
    }
}

