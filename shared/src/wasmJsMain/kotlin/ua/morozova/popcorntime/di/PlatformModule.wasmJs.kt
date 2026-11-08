package ua.morozova.popcorntime.di

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import eu.anifantakis.lib.ksafe.KSafe
import org.koin.core.module.Module
import org.koin.dsl.module

@Composable
actual fun rememberPlatformModule(): Module = remember {
    module { single { KSafe() } }
}