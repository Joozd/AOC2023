package nl.joozd.aoc2023.ui.activities.mainactivity

import androidx.lifecycle.ViewModel
import nl.joozd.aoc2023.common.Solution

class MainActivityViewModel: ViewModel() {
    // Cache data
    val cachedData = HashMap<Int, Solution.SolutionData>()
}