

package com.example.android.todoapp.module.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.example.android.todoapp.R


@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    scaffoldState: ScaffoldState = rememberScaffoldState()
) {
    Scaffold(
        scaffoldState = scaffoldState,
        modifier = modifier.fillMaxSize(),
        topBar = {

        },
        floatingActionButton = {

        }
    ) {
        Box(
            Modifier.padding(it)
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(all = dimensionResource(id = R.dimen.horizontal_margin))
                .verticalScroll(rememberScrollState()),
            contentAlignment = Alignment.Center,
        ) {

            CircularProgressIndicator(
                modifier = Modifier.width(32.dp),
                strokeWidth = 3.dp,
                color = Color.Black,
            )
        }
    }
}

