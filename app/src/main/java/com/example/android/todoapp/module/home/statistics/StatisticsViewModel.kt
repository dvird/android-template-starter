

package com.example.android.todoapp.module.home.statistics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.todoapp.R
import com.example.android.todoapp.data.task.Task
import com.example.android.todoapp.data.task.TaskRepository
import com.example.android.todoapp.support.Async
import com.example.android.todoapp.support.coroutines.WhileUiSubscribed
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * UiState for the statistics screen.
 */
data class StatisticsUiState(
    val isEmpty: Boolean = false,
    val isLoading: Boolean = false,
    val activeTasksPercent: Float = 0f,
    val completedTasksPercent: Float = 0f
)

/**
 * ViewModel for the statistics screen.
 */
@HiltViewModel
class StatisticsViewModel @Inject constructor(
    private val taskRepository: TaskRepository
) : ViewModel() {

    val uiState: StateFlow<StatisticsUiState> =
        taskRepository.getTasksStream()
            .map { Async.Success(it) }
            .catch<Async<List<Task>>> { emit(Async.Error(R.string.loading_tasks_error)) }
            .map { taskAsync -> produceStatisticsUiState(taskAsync) }
            .stateIn(
                scope = viewModelScope,
                started = WhileUiSubscribed,
                initialValue = StatisticsUiState(isLoading = true)
            )

    fun refresh() {
        viewModelScope.launch {
            taskRepository.refresh()
        }
    }

    private fun produceStatisticsUiState(taskLoad: Async<List<Task>>) =
        when (taskLoad) {
            Async.Loading -> {
                StatisticsUiState(isLoading = true, isEmpty = true)
            }
            is Async.Error -> {
                // TODO: Show error message?
                StatisticsUiState(isEmpty = true, isLoading = false)
            }
            is Async.Success -> {
                val stats = getActiveAndCompletedStats(taskLoad.data)
                StatisticsUiState(
                    isEmpty = taskLoad.data.isEmpty(),
                    activeTasksPercent = stats.activeTasksPercent,
                    completedTasksPercent = stats.completedTasksPercent,
                    isLoading = false
                )
            }
        }
}
