package ua.morozova.popcorntime

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.resources.painterResource

import popcorntime.shared.generated.resources.Res
import popcorntime.shared.generated.resources.compose_multiplatform
import ua.morozova.popcorntime.ui.onboarding.OnboardingScreen
import ua.morozova.popcorntime.ui.theme.AppTheme

@Composable
@Preview
fun App() {
    AppTheme {
        var showOnboarding by remember { mutableStateOf(true) }

        if (showOnboarding) {
            OnboardingScreen(
                onFinishClicked = {
                    showOnboarding = false
                }
            )
        } else {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Головний екран PopcornTime", style = MaterialTheme.typography.headlineMedium)
            }
        }
    }
}