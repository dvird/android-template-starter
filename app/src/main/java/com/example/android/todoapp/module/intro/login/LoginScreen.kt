package com.example.android.todoapp.module.intro.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ContentAlpha
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.android.todoapp.R
import com.example.android.todoapp.module.home.AddEditTaskTopAppBar
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState


@Composable
fun LoginScreen(
    onAuthSuccess: () -> Unit,
    modifier: Modifier = Modifier,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    viewModel: LoginViewModel = hiltViewModel()
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        scaffoldState = scaffoldState,
        topBar = { AddEditTaskTopAppBar(R.string.login, null) },
        floatingActionButton = {
            FloatingActionButton(onClick = viewModel::login) {
                Icon(Icons.Filled.Done, stringResource(id = R.string.cd_save_task))
            }
        }
    ) { paddingValues ->
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        LoginContent(
            loading = uiState.isLoading,
            username = uiState.username,
            password = uiState.password,
            onUsernameChanged = viewModel::updateUsername,
            onPasswordChanged = viewModel::updatePassword,
            modifier = Modifier.padding(paddingValues)
        )

        // Check if the task is saved and call onTaskUpdate event
        LaunchedEffect(uiState.isLoggedIn) {
            if (uiState.isLoggedIn) {
                onAuthSuccess()
            }
        }

        // Check for user messages to display on the screen
        uiState.userMessage?.let { userMessage ->
            val snackbarText = stringResource(userMessage)
            LaunchedEffect(scaffoldState, viewModel, userMessage, snackbarText) {
                scaffoldState.snackbarHostState.showSnackbar(snackbarText)
                viewModel.snackbarMessageShown()
            }
        }
    }
}

@Composable
private fun LoginContent(
    loading: Boolean,
    username: String,
    password: String,
    onUsernameChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    if (loading) {
        SwipeRefresh(
            // Show the loading spinner—`loading` is `true` in this code path
            state = rememberSwipeRefreshState(true),
            onRefresh = { /* DO NOTHING */ },
            content = { },
        )
    } else {
        Column(
            modifier
                .fillMaxWidth()
                .padding(all = dimensionResource(id = R.dimen.horizontal_margin))
                .verticalScroll(rememberScrollState())
        ) {
            val textFieldColors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Black,
                cursorColor = MaterialTheme.colors.secondary.copy(alpha = ContentAlpha.high)
            )
            TextField(
                value = username,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = onUsernameChanged,
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.username),
                        style = MaterialTheme.typography.h6
                    )
                },
                textStyle = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.Bold),
                maxLines = 1,
                colors = textFieldColors
            )
            TextField(
                value = password,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = onPasswordChanged,
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.password),
                        style = MaterialTheme.typography.h6
                    )
                },
                textStyle = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.Bold),
                maxLines = 1,
                colors = textFieldColors
            )
        }
    }
}
