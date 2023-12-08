

package com.example.android.todoapp.addedittask

import androidx.lifecycle.SavedStateHandle
import com.example.android.todoapp.MainCoroutineRule
import com.example.android.todoapp.R.string
import com.example.android.todoapp.navigation.TodoDestinationsArgs
import com.example.android.todoapp.data.FakeTaskRepository
import com.example.android.todoapp.data.task.Task
import com.example.android.todoapp.module.intro.login.LoginViewModel
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Unit tests for the implementation of [LoginViewModel].
 */
@ExperimentalCoroutinesApi
class AddEditTaskViewModelTest {

    // Subject under test
    private lateinit var loginViewModel: LoginViewModel

    // Use a fake repository to be injected into the viewmodel
    private lateinit var tasksRepository: FakeTaskRepository
    private val task = Task(title = "Title1", description = "Description1", id = "0")

    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setupViewModel() {
        // We initialise the repository with no tasks
        tasksRepository = FakeTaskRepository().apply {
            addTasks(task)
        }
    }

    @Test
    fun saveNewTaskToRepository_showsSuccessMessageUi() {
        loginViewModel = LoginViewModel(
            tasksRepository,
            SavedStateHandle(mapOf(TodoDestinationsArgs.TASK_ID_ARG to "0"))
        )

        val newTitle = "New Task Title"
        val newDescription = "Some Task Description"
        loginViewModel.apply {
            updateUsername(newTitle)
            updatePassword(newDescription)
        }
        loginViewModel.login()

        val newTask = tasksRepository.savedTasks.value.values.first()

        // Then a task is saved in the repository and the view updated
        assertThat(newTask.title).isEqualTo(newTitle)
        assertThat(newTask.description).isEqualTo(newDescription)
    }

    @Test
    fun loadTasks_loading() = runTest {
        // Set Main dispatcher to not run coroutines eagerly, for just this one test
        Dispatchers.setMain(StandardTestDispatcher())

        loginViewModel = LoginViewModel(
            tasksRepository,
            SavedStateHandle(mapOf(TodoDestinationsArgs.TASK_ID_ARG to "0"))
        )

        // Then progress indicator is shown
        assertThat(loginViewModel.uiState.value.isLoading).isTrue()

        // Execute pending coroutines actions
        advanceUntilIdle()

        // Then progress indicator is hidden
        assertThat(loginViewModel.uiState.value.isLoading).isFalse()
    }

    @Test
    fun loadTasks_taskShown() {
        loginViewModel = LoginViewModel(
            tasksRepository,
            SavedStateHandle(mapOf(TodoDestinationsArgs.TASK_ID_ARG to "0"))
        )

        // Add task to repository
        tasksRepository.addTasks(task)

        // Verify a task is loaded
        val uiState = loginViewModel.uiState.value
        assertThat(uiState.username).isEqualTo(task.title)
        assertThat(uiState.password).isEqualTo(task.description)
        assertThat(uiState.isLoading).isFalse()
    }

    @Test
    fun saveNewTaskToRepository_emptyTitle_error() {
        loginViewModel = LoginViewModel(
            tasksRepository,
            SavedStateHandle(mapOf(TodoDestinationsArgs.TASK_ID_ARG to "0"))
        )

        saveTaskAndAssertUserMessage("", "Some Task Description")
    }

    @Test
    fun saveNewTaskToRepository_emptyDescription_error() {
        loginViewModel = LoginViewModel(
            tasksRepository,
            SavedStateHandle(mapOf(TodoDestinationsArgs.TASK_ID_ARG to "0"))
        )

        saveTaskAndAssertUserMessage("Title", "")
    }

    @Test
    fun saveNewTaskToRepository_emptyDescriptionEmptyTitle_error() {
        loginViewModel = LoginViewModel(
            tasksRepository,
            SavedStateHandle(mapOf(TodoDestinationsArgs.TASK_ID_ARG to "0"))
        )

        saveTaskAndAssertUserMessage("", "")
    }

    private fun saveTaskAndAssertUserMessage(title: String, description: String) {
        loginViewModel.apply {
            updateUsername(title)
            updatePassword(description)
        }

        // When saving an incomplete task
        loginViewModel.login()

        assertThat(
            loginViewModel.uiState.value.userMessage
        ).isEqualTo(string.empty_task_message)
    }
}
