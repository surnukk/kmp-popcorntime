package ua.morozova.popcorntime.ui.auth

import ua.morozova.popcorntime.domain.ValidationError

object AuthContract {

    data class State(
        val username: String = "",
        val password: String = "",
        val usernameError: ValidationError? = null,
        val passwordError: ValidationError? = null,
        val isPasswordVisible: Boolean = false
    )

    sealed interface Intent {
        data class UsernameChanged(val value: String) : Intent
        data class PasswordChanged(val value: String) : Intent
        data object TogglePasswordVisibility : Intent
        data object SignInClicked : Intent
        data object SignUpClicked : Intent
        data object BackClicked : Intent
    }

    sealed interface Effect {
        data class NavigateToHome(val username: String) : Effect
        data object NavigateToRegister : Effect
        data object NavigateBack : Effect
    }
}