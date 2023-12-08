

package com.example.android.todoapp.statistics

import androidx.compose.material.Surface
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.android.todoapp.HiltTestActivity
import com.example.android.todoapp.R
import com.example.android.todoapp.data.task.TaskRepository
import com.example.android.todoapp.module.home.statistics.StatisticsScreen
import com.example.android.todoapp.module.home.statistics.StatisticsViewModel
import com.google.accompanist.themeadapter.appcompat.AppCompatTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Integration test for the statistics screen.
 */
@RunWith(AndroidJUnit4::class)
@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class StatisticsScreenTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<HiltTestActivity>()
    private val activity get() = composeTestRule.activity

    @Inject
    lateinit var repository: TaskRepository

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun tasks_showsNonEmptyMessage() = runTest {
        // Given some tasks
        repository.apply {
            createTask("Title1", "Description1")
            createTask("Title2", "Description2").also {
                completeTask(it)
            }
        }

        composeTestRule.setContent {
            AppCompatTheme {
                Surface {
                    StatisticsScreen(
                        openDrawer = { },
                        viewModel = StatisticsViewModel(repository)
                    )
                }
            }
        }

        val expectedActiveTaskText = activity.getString(R.string.statistics_active_tasks, 50.0f)
        val expectedCompletedTaskText = activity
            .getString(R.string.statistics_completed_tasks, 50.0f)

        // check that both info boxes are displayed and contain the correct info
        composeTestRule.onNodeWithText(expectedActiveTaskText).assertIsDisplayed()
        composeTestRule.onNodeWithText(expectedCompletedTaskText).assertIsDisplayed()
    }
}
