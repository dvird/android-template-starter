package com.example.android.todoapp.navigation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.todoapp.service.auth.AppAuthService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


data class NavUiState(
    val destination: String? = null,
)

/**
 * ViewModel for the nav control.
 */
@HiltViewModel
class MainNavViewModel @Inject constructor(
    private val authService: AppAuthService,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    // A MutableStateFlow needs to be created in this ViewModel. The source of truth of the current
    // editable Task is the ViewModel, we need to mutate the UI state directly in methods such as
    // `updateTitle` or `updateDescription`
    private val _uiState = MutableStateFlow(NavUiState())
    val uiState: StateFlow<NavUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            delay(2 * 1000)

            authService.isLoggedIn()
                .collect { isLoggedIn ->
                var destination = TodoDestinations.TASKS_ROUTE
                if (!isLoggedIn) {
                    destination = TodoDestinations.LOGIN_ROUTE
                }

                _uiState.update {
                    it.copy(destination = destination)
                }
            }
        }
    }
}
