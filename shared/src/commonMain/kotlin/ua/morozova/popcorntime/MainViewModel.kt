package ua.morozova.popcorntime

import androidx.lifecycle.ViewModel
import ua.morozova.popcorntime.domain.usecase.IsOnboardingCompletedUseCase
import ua.morozova.popcorntime.navigation.Screen

class MainViewModel(
    isOnboardingCompleted: IsOnboardingCompletedUseCase
) : ViewModel() {

    val startScreen: Screen =
        if (isOnboardingCompleted()) Screen.Auth else Screen.Onboarding
}