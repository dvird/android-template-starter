

package com.example.android.todoapp.module.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.android.todoapp.navigation.MainNavGraph
import com.google.accompanist.themeadapter.appcompat.AppCompatTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * Main activity for the todoapp
 */
@AndroidEntryPoint
class TodoActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppCompatTheme {
                MainNavGraph()
            }
        }
    }
}
