package com.example.android.todoapp.navigation.intro

import androidx.compose.material.DrawerState
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.android.todoapp.module.intro.login.LoginScreen
import com.example.android.todoapp.navigation.TodoDestinations
import com.example.android.todoapp.navigation.TodoNavigationActions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun NavGraphBuilder.introNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    coroutineScope: CoroutineScope,
    drawerState: DrawerState,
    route: String,
    navActions: TodoNavigationActions,
) {

    composable(TodoDestinations.LOGIN_ROUTE) {
        LoginScreen(onAuthSuccess = { coroutineScope.launch { drawerState.open() } })
    }
}