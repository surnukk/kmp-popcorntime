package ua.morozova.popcorntime.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ua.morozova.popcorntime.ui.register.RegisterContract.Effect
import ua.morozova.popcorntime.ui.register.RegisterContract.Intent
import ua.morozova.popcorntime.ui.register.RegisterContract.State

class RegisterViewModel : ViewModel() {

    private val _state = MutableStateFlow(State())
    val state: StateFlow<State> = _state.asStateFlow()

    private val _effect = Channel<Effect>(Channel.BUFFERED)
    val effect: Flow<Effect> = _effect.receiveAsFlow()

    fun onIntent(intent: Intent) {
        when (intent) {
            is Intent.UsernameChanged -> _state.update { it.copy(username = intent.value) }
            is Intent.EmailChanged -> _state.update { it.copy(email = intent.value) }
            is Intent.PasswordChanged -> _state.update { it.copy(password = intent.value) }
            is Intent.ConfirmPasswordChanged -> _state.update { it.copy(confirmPassword = intent.value) }
            Intent.TogglePasswordVisibility -> _state.update { it.copy(isPasswordVisible = !it.isPasswordVisible) }
            Intent.RegisterClicked -> Unit

            Intent.SignInClicked, Intent.BackClicked ->
                viewModelScope.launch { _effect.send(Effect.NavigateBack) }
        }
    }
}