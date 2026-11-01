package ua.morozova.popcorntime.di

import com.russhwolf.settings.Settings
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import ua.morozova.popcorntime.data.OnboardingRepositoryImpl
import ua.morozova.popcorntime.domain.OnboardingRepository
import ua.morozova.popcorntime.domain.usecase.IsOnboardingCompletedUseCase
import ua.morozova.popcorntime.domain.usecase.SetOnboardingCompletedUseCase
import ua.morozova.popcorntime.domain.usecase.ValidatePasswordUseCase
import ua.morozova.popcorntime.domain.usecase.ValidateUsernameUseCase
import ua.morozova.popcorntime.settings.AppSettings
import ua.morozova.popcorntime.ui.auth.AuthViewModel
import ua.morozova.popcorntime.MainViewModel
import ua.morozova.popcorntime.ui.onboarding.OnboardingViewModel
import ua.morozova.popcorntime.ui.register.RegisterViewModel

val appModule = module {
    single<Settings> { Settings() }
    single { AppSettings(get()) }

    single<OnboardingRepository> { OnboardingRepositoryImpl(get()) }

    factory { IsOnboardingCompletedUseCase(get()) }
    factory { SetOnboardingCompletedUseCase(get()) }
    factory { ValidateUsernameUseCase() }
    factory { ValidatePasswordUseCase() }

    viewModelOf(::MainViewModel)
    viewModelOf(::OnboardingViewModel)
    viewModelOf(::AuthViewModel)
    viewModelOf(::RegisterViewModel)
}