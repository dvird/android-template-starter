package com.example.android.todoapp.module.home.statistics

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.android.todoapp.R
import com.example.android.todoapp.module.home.StatisticsTopAppBar
import com.example.android.todoapp.support.util.LoadingContent
import com.google.accompanist.themeadapter.appcompat.AppCompatTheme


@Composable
fun StatisticsScreen(
    openDrawer: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: StatisticsViewModel = hiltViewModel(),
    scaffoldState: ScaffoldState = rememberScaffoldState()
) {
    Scaffold(scaffoldState = scaffoldState,
        topBar = { StatisticsTopAppBar(openDrawer) }) { paddingValues ->
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        StatisticsContent(
            loading = uiState.isLoading,
            empty = uiState.isEmpty,
            activeTasksPercent = uiState.activeTasksPercent,
            completedTasksPercent = uiState.completedTasksPercent,
            onRefresh = { viewModel.refresh() },
            modifier = modifier.padding(paddingValues)
        )
    }
}

@Composable
private fun StatisticsContent(
    loading: Boolean,
    empty: Boolean,
    activeTasksPercent: Float,
    completedTasksPercent: Float,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier
) {
    val commonModifier = modifier
        .fillMaxWidth()
        .padding(all = dimensionResource(id = R.dimen.horizontal_margin))

    LoadingContent(loading = loading,
        empty = empty,
        onRefresh = onRefresh,
        modifier = modifier,
        emptyContent = {
            Text(
                text = stringResource(id = R.string.statistics_no_tasks), modifier = commonModifier
            )
        }) {
        Column(
            commonModifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            if (!loading) {
                Text(stringResource(id = R.string.statistics_active_tasks, activeTasksPercent))
                Text(
                    stringResource(
                        id = R.string.statistics_completed_tasks, completedTasksPercent
                    )
                )
            }
        }
    }
}

@Preview
@Composable
fun StatisticsContentPreview() {
    AppCompatTheme {
        Surface {
            StatisticsContent(loading = false,
                empty = false,
                activeTasksPercent = 80f,
                completedTasksPercent = 20f,
                onRefresh = { })
        }
    }
}

@Preview
@Composable
fun StatisticsContentEmptyPreview() {
    AppCompatTheme {
        Surface {
            StatisticsContent(loading = false,
                empty = true,
                activeTasksPercent = 0f,
                completedTasksPercent = 0f,
                onRefresh = { })
        }
    }
}
