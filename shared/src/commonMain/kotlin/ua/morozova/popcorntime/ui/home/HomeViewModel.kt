package ua.morozova.popcorntime.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ua.morozova.popcorntime.domain.usecase.auth.GetCurrentUserUseCase
import ua.morozova.popcorntime.domain.usecase.auth.LogoutUseCase

class HomeViewModel(
    getCurrentUser: GetCurrentUserUseCase,
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {

    private val _userName = MutableStateFlow<String?>(null)
    val userName: StateFlow<String?> = _userName.asStateFlow()

    init {
        viewModelScope.launch {
            getCurrentUser().onSuccess { _userName.value = it.firstName }
        }
    }

    fun onLogoutClicked() = logoutUseCase()
}