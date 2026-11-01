package ua.morozova.popcorntime.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import org.koin.compose.viewmodel.koinViewModel
import ua.morozova.popcorntime.ui.auth.AuthScreen
import ua.morozova.popcorntime.ui.home.HomeScreen
import ua.morozova.popcorntime.MainViewModel
import ua.morozova.popcorntime.ui.onboarding.OnboardingScreen
import ua.morozova.popcorntime.ui.register.RegisterScreen
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier

private val navSavedStateConfig = SavedStateConfiguration {
    serializersModule = SerializersModule {
        polymorphic(NavKey::class) {
            subclass(Screen.Onboarding::class, Screen.Onboarding.serializer())
            subclass(Screen.Auth::class, Screen.Auth.serializer())
            subclass(Screen.Register::class, Screen.Register.serializer())
            subclass(Screen.Home::class, Screen.Home.serializer())
        }
    }
}

@Composable
fun AppNavigation(
    mainViewModel: MainViewModel = koinViewModel()
) {
    val backStack = rememberNavBackStack(navSavedStateConfig, mainViewModel.startScreen)

    NavDisplay(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = entryProvider {
            entry<Screen.Onboarding> {
                OnboardingScreen(
                    onNavigateToAuth = { backStack.add(Screen.Auth) }
                )
            }
            entry<Screen.Auth> {
                AuthScreen(
                    onNavigateToHome = { username -> backStack.resetTo(Screen.Home(username)) },
                    onNavigateToRegister = { backStack.add(Screen.Register) },
                    onNavigateBack = {
                        if (backStack.size > 1) backStack.removeLastOrNull()
                        else backStack.resetTo(Screen.Onboarding)
                    }
                )
            }
            entry<Screen.Register> {
                RegisterScreen(
                    onNavigateBack = { backStack.removeLastOrNull() }
                )
            }
            entry<Screen.Home> { key ->
                HomeScreen(username = key.username)
            }
        }
    )
}

private fun MutableList<NavKey>.resetTo(screen: Screen) {
    clear()
    add(screen)
}