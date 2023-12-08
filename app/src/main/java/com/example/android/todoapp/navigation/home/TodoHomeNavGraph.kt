package com.example.android.todoapp.navigation.home

import android.app.Activity
import androidx.compose.material.DrawerState
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.android.todoapp.R
import com.example.android.todoapp.module.home.AppModalDrawer
import com.example.android.todoapp.module.home.addedittask.AddEditTaskScreen
import com.example.android.todoapp.module.home.statistics.StatisticsScreen
import com.example.android.todoapp.module.home.taskdetail.TaskDetailScreen
import com.example.android.todoapp.module.home.tasks.TasksScreen
import com.example.android.todoapp.navigation.TodoDestinations
import com.example.android.todoapp.navigation.TodoDestinationsArgs.TASK_ID_ARG
import com.example.android.todoapp.navigation.TodoDestinationsArgs.TITLE_ARG
import com.example.android.todoapp.navigation.TodoDestinationsArgs.USER_MESSAGE_ARG
import com.example.android.todoapp.navigation.TodoNavigationActions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun NavGraphBuilder.homeNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    coroutineScope: CoroutineScope,
    drawerState: DrawerState,
    startDestination: String = TodoDestinations.TASKS_ROUTE,
    navActions: TodoNavigationActions,
) {
    val currentNavBackStackEntry = navController.currentBackStackEntry
    val currentRoute = currentNavBackStackEntry?.destination?.route ?: startDestination

    composable(
        TodoDestinations.TASKS_ROUTE,
        arguments = listOf(
            navArgument(USER_MESSAGE_ARG) { type = NavType.IntType; defaultValue = 0 }
        )
    ) { entry ->
        AppModalDrawer(drawerState, currentRoute, navActions) {
            TasksScreen(
                userMessage = entry.arguments?.getInt(USER_MESSAGE_ARG)!!,
                onUserMessageDisplayed = { entry.arguments?.putInt(USER_MESSAGE_ARG, 0) },
                onAddTask = { navActions.navigateToAddEditTask(R.string.add_task, null) },
                onTaskClick = { task -> navActions.navigateToTaskDetail(task.id) },
                openDrawer = { coroutineScope.launch { drawerState.open() } }
            )
        }
    }
    composable(TodoDestinations.STATISTICS_ROUTE) {
        AppModalDrawer(drawerState, currentRoute, navActions) {
            StatisticsScreen(openDrawer = { coroutineScope.launch { drawerState.open() } })
        }
    }
    composable(
        TodoDestinations.ADD_EDIT_TASK_ROUTE,
        arguments = listOf(
            navArgument(TITLE_ARG) { type = NavType.IntType },
            navArgument(TASK_ID_ARG) { type = NavType.StringType; nullable = true },
        )
    ) { entry ->
        val taskId = entry.arguments?.getString(TASK_ID_ARG)
        AddEditTaskScreen(
            topBarTitle = entry.arguments?.getInt(TITLE_ARG)!!,
            onTaskUpdate = {
                navActions.navigateToTasks(
                    if (taskId == null) ADD_EDIT_RESULT_OK else EDIT_RESULT_OK
                )
            },
            onBack = { navController.popBackStack() }
        )
    }
    composable(TodoDestinations.TASK_DETAIL_ROUTE) {
        TaskDetailScreen(
            onEditTask = { taskId ->
                navActions.navigateToAddEditTask(R.string.edit_task, taskId)
            },
            onBack = { navController.popBackStack() },
            onDeleteTask = { navActions.navigateToTasks(DELETE_RESULT_OK) }
        )
    }
}

// Keys for navigation
const val ADD_EDIT_RESULT_OK = Activity.RESULT_FIRST_USER + 1
const val DELETE_RESULT_OK = Activity.RESULT_FIRST_USER + 2
const val EDIT_RESULT_OK = Activity.RESULT_FIRST_USER + 3
