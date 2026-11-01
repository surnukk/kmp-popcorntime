package ua.morozova.popcorntime

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication
import ua.morozova.popcorntime.di.appModule
import ua.morozova.popcorntime.navigation.AppNavigation
import ua.morozova.popcorntime.ui.theme.AppTheme

@Composable
@Preview
fun App() {
    KoinApplication(application = { modules(appModule) }) {
        AppTheme {
            AppNavigation()
        }
    }
}