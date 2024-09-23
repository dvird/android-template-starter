package com.example.android.todoapp.support.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Display an initial empty state or pull-to-refresh content.
 *
 * @param loading Indicates whether the content is loading. A loading spinner is displayed if true.
 * @param empty Indicates whether the content is empty. If true, displays emptyContent.
 * @param emptyContent Composable function to display in case the content is empty.
 * @param onRefresh Lambda function that triggers the refresh event.
 * @param modifier Modifier applied to the layout.
 * @param content The main content to display when not empty.
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LoadingContent(
    loading: Boolean,
    empty: Boolean,
    emptyContent: @Composable () -> Unit,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    if (empty) {
        emptyContent()
    } else {
        val pullRefreshState = rememberPullRefreshState(refreshing = loading, onRefresh = onRefresh)

        Box(
            modifier = modifier
                .pullRefresh(pullRefreshState) // Attach the pullRefresh modifier to the Box
                .fillMaxSize()
        ) {
            // Main content of the screen
            content()

            // Display the PullRefreshIndicator at the top of the content
            PullRefreshIndicator(
                refreshing = loading,
                state = pullRefreshState,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(16.dp)
            )
        }
    }
}
