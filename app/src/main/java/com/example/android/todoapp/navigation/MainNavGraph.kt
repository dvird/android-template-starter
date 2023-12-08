package com.example.android.todoapp.navigation

import androidx.compose.material.DrawerState
import androidx.compose.material.DrawerValue
import androidx.compose.material.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.android.todoapp.navigation.home.homeNavGraph
import com.example.android.todoapp.navigation.intro.introNavGraph
import com.example.android.todoapp.navigation.splash.splashNavGraph
import kotlinx.coroutines.CoroutineScope

@Composable
fun MainNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    startDestination: String = TodoDestinations.SPLASH_ROUTE,
    navActions: TodoNavigationActions = remember(navController) {
        TodoNavigationActions(navController)
    },
) {

    val navigator = hiltViewModel<MainNavViewModel>()
    val navigatorState = navigator.uiState.collectAsStateWithLifecycle()
    val finalDestination = navigatorState.value.destination

    LaunchedEffect(finalDestination) {
        if (finalDestination != null) {
            navController.navigate(finalDestination) {
                popUpTo(0)
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        splashNavGraph(
            navController = navController,
            coroutineScope = coroutineScope,
            drawerState = drawerState,
            navActions = navActions
        )
        introNavGraph(
            navController = navController,
            coroutineScope = coroutineScope,
            drawerState = drawerState,
            navActions = navActions,
            route = TodoDestinations.AUTH_ROUTE
        )
        homeNavGraph(
            navController = navController,
            coroutineScope = coroutineScope,
            drawerState = drawerState,
            navActions = navActions
        )
    }
}