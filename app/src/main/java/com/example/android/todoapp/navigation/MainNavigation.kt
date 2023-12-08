

package com.example.android.todoapp.navigation

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.android.todoapp.navigation.TodoDestinationsArgs.TASK_ID_ARG
import com.example.android.todoapp.navigation.TodoDestinationsArgs.TITLE_ARG
import com.example.android.todoapp.navigation.TodoDestinationsArgs.USER_MESSAGE_ARG
import com.example.android.todoapp.navigation.TodoScreens.ADD_EDIT_TASK_SCREEN
import com.example.android.todoapp.navigation.TodoScreens.STATISTICS_SCREEN
import com.example.android.todoapp.navigation.TodoScreens.TASKS_SCREEN
import com.example.android.todoapp.navigation.TodoScreens.TASK_DETAIL_SCREEN

/**
 * Screens used in [TodoDestinations]
 */
private object TodoScreens {
    const val TASKS_SCREEN = "tasks"
    const val STATISTICS_SCREEN = "statistics"
    const val TASK_DETAIL_SCREEN = "task"
    const val ADD_EDIT_TASK_SCREEN = "addEditTask"
}

/**
 * Arguments used in [TodoDestinations] routes
 */
object TodoDestinationsArgs {
    const val USER_MESSAGE_ARG = "userMessage"
    const val TASK_ID_ARG = "taskId"
    const val TITLE_ARG = "title"
}

/**
 * Destinations used in the [TodoActivity]
 */
object TodoDestinations {
    const val SPLASH_ROUTE = "splash"

    const val AUTH_ROUTE = "auth"
    const val LOGIN_ROUTE = "login"


    const val TASKS_ROUTE = "$TASKS_SCREEN?$USER_MESSAGE_ARG={$USER_MESSAGE_ARG}"
    const val STATISTICS_ROUTE = STATISTICS_SCREEN
    const val TASK_DETAIL_ROUTE = "$TASK_DETAIL_SCREEN/{$TASK_ID_ARG}"
    const val ADD_EDIT_TASK_ROUTE = "$ADD_EDIT_TASK_SCREEN/{$TITLE_ARG}?$TASK_ID_ARG={$TASK_ID_ARG}"
}

/**
 * Models the navigation actions in the app.
 */
class TodoNavigationActions(private val navController: NavHostController) {

    fun navigateToTasks(userMessage: Int = 0) {
        val navigatesFromDrawer = userMessage == 0
        navController.navigate(
            TASKS_SCREEN.let {
                if (userMessage != 0) "$it?$USER_MESSAGE_ARG=$userMessage" else it
            }
        ) {
            popUpTo(navController.graph.findStartDestination().id) {
                inclusive = !navigatesFromDrawer
                saveState = navigatesFromDrawer
            }
            launchSingleTop = true
            restoreState = navigatesFromDrawer
        }
    }

    fun navigateToStatistics() {
        navController.navigate(TodoDestinations.STATISTICS_ROUTE) {
            // Pop up to the start destination of the graph to
            // avoid building up a large stack of destinations
            // on the back stack as users select items
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            // Avoid multiple copies of the same destination when
            // reselecting the same item
            launchSingleTop = true
            // Restore state when reselecting a previously selected item
            restoreState = true
        }
    }

    fun navigateToTaskDetail(taskId: String) {
        navController.navigate("$TASK_DETAIL_SCREEN/$taskId")
    }

    fun navigateToAddEditTask(title: Int, taskId: String?) {
        navController.navigate(
            "$ADD_EDIT_TASK_SCREEN/$title".let {
                if (taskId != null) "$it?$TASK_ID_ARG=$taskId" else it
            }
        )
    }

    fun navigateToAuth() {

    }

    fun navigateToHome() {
        //navController.navigate()
    }
}
