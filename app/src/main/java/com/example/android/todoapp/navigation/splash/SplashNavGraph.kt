package com.example.android.todoapp.navigation.splash

import androidx.compose.material.DrawerState
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.android.todoapp.module.splash.SplashScreen
import com.example.android.todoapp.navigation.TodoDestinations
import com.example.android.todoapp.navigation.TodoNavigationActions
import kotlinx.coroutines.CoroutineScope

fun NavGraphBuilder.splashNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    coroutineScope: CoroutineScope,
    drawerState: DrawerState,
    startDestination: String = TodoDestinations.SPLASH_ROUTE,
    navActions: TodoNavigationActions,
) {

    composable(TodoDestinations.SPLASH_ROUTE) {
        SplashScreen()
    }
}