package ua.morozova.popcorntime

import androidx.compose.runtime.Composable
import org.koin.compose.KoinApplication
import org.koin.dsl.koinConfiguration
import ua.morozova.popcorntime.di.appModule
import ua.morozova.popcorntime.di.rememberPlatformModule
import ua.morozova.popcorntime.navigation.AppNavigation
import ua.morozova.popcorntime.ui.theme.AppTheme

@Composable
fun App() {
    val platformModule = rememberPlatformModule()

    KoinApplication(
        configuration = koinConfiguration { modules(platformModule, appModule) },
        content = {
            AppTheme {
                AppNavigation()
            }
        }
    )
}