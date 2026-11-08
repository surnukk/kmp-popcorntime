package ua.morozova.popcorntime.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface Screen : NavKey {

    @Serializable
    data object Onboarding : Screen

    @Serializable
    data object Auth : Screen

    @Serializable
    data object Register : Screen

    @Serializable
    data object Home : Screen
}