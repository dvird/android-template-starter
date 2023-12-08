

package com.example.android.todoapp.statistics

import com.example.android.todoapp.MainCoroutineRule
import com.example.android.todoapp.data.FakeTaskRepository
import com.example.android.todoapp.data.task.Task
import com.example.android.todoapp.module.home.statistics.StatisticsViewModel
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Unit tests for the implementation of [StatisticsViewModel]
 */
@ExperimentalCoroutinesApi
class StatisticsViewModelTest {

    // Subject under test
    private lateinit var statisticsViewModel: StatisticsViewModel

    // Use a fake repository to be injected into the viewmodel
    private lateinit var tasksRepository: FakeTaskRepository

    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setupStatisticsViewModel() {
        tasksRepository = FakeTaskRepository()
        statisticsViewModel = StatisticsViewModel(tasksRepository)
    }

    @Test
    fun loadEmptyTasksFromRepository_EmptyResults() = runTest {
        // Given an initialized StatisticsViewModel with no tasks

        // Then the results are empty
        val uiState = statisticsViewModel.uiState.first()
        assertThat(uiState.isEmpty).isTrue()
    }

    @Test
    fun loadNonEmptyTasksFromRepository_NonEmptyResults() = runTest {
        // We initialise the tasks to 3, with one active and two completed
        val task1 = Task(id = "1", title = "Title1", description = "Desc1")
        val task2 = Task(id = "2", title = "Title2", description = "Desc2", isCompleted = true)
        val task3 = Task(id = "3", title = "Title3", description = "Desc3", isCompleted = true)
        val task4 = Task(id = "4", title = "Title4", description = "Desc4", isCompleted = true)
        tasksRepository.addTasks(task1, task2, task3, task4)

        // Then the results are not empty
        val uiState = statisticsViewModel.uiState.first()
        assertThat(uiState.isEmpty).isFalse()
        assertThat(uiState.activeTasksPercent).isEqualTo(25f)
        assertThat(uiState.completedTasksPercent).isEqualTo(75f)
        assertThat(uiState.isLoading).isEqualTo(false)
    }

    @Test
    fun loadTasks_loading() = runTest {
        // Set Main dispatcher to not run coroutines eagerly, for just this one test
        Dispatchers.setMain(StandardTestDispatcher())

        var isLoading: Boolean? = true
        val job = launch {
            statisticsViewModel.uiState.collect {
                isLoading = it.isLoading
            }
        }

        // Then progress indicator is shown
        assertThat(isLoading).isTrue()

        // Execute pending coroutines actions
        advanceUntilIdle()

        // Then progress indicator is hidden
        assertThat(isLoading).isFalse()
        job.cancel()
    }
}
