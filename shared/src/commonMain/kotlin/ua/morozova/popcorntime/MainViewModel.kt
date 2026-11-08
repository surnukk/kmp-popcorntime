package ua.morozova.popcorntime

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.StateFlow
import ua.morozova.popcorntime.domain.usecase.auth.IsAuthorizedUseCase
import ua.morozova.popcorntime.domain.usecase.IsOnboardingCompletedUseCase
import ua.morozova.popcorntime.navigation.Screen

class MainViewModel(
    isOnboardingCompleted: IsOnboardingCompletedUseCase,
    isAuthorized: IsAuthorizedUseCase
) : ViewModel() {

    val isLoggedIn: StateFlow<Boolean> = isAuthorized()

    val startScreen: Screen = when {
        isLoggedIn.value -> Screen.Home
        isOnboardingCompleted() -> Screen.Auth
        else -> Screen.Onboarding
    }
}