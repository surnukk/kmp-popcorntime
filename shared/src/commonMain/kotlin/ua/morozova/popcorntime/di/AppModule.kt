package ua.morozova.popcorntime.di

import com.russhwolf.settings.Settings
import io.ktor.client.HttpClient
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import ua.morozova.popcorntime.MainViewModel
import ua.morozova.popcorntime.data.AppSettings
import ua.morozova.popcorntime.data.OnboardingRepositoryImpl
import ua.morozova.popcorntime.data.auth.AppAuthRepository
import ua.morozova.popcorntime.data.network.AuthorizedApiClient
import ua.morozova.popcorntime.data.network.createHttpClient
import ua.morozova.popcorntime.data.profile.AppProfileRepository
import ua.morozova.popcorntime.data.profile.service.ProfileApiService
import ua.morozova.popcorntime.data.security.AppSecureStorage
import ua.morozova.popcorntime.data.security.SecureStorage
import ua.morozova.popcorntime.data.auth.service.AppAuthApiService
import ua.morozova.popcorntime.data.auth.service.AuthApiService
import ua.morozova.popcorntime.domain.OnboardingRepository
import ua.morozova.popcorntime.domain.auth.AuthRepository
import ua.morozova.popcorntime.domain.profile.ProfileRepository
import ua.morozova.popcorntime.domain.usecase.*
import ua.morozova.popcorntime.domain.usecase.auth.GetCurrentUserUseCase
import ua.morozova.popcorntime.domain.usecase.auth.IsAuthorizedUseCase
import ua.morozova.popcorntime.domain.usecase.auth.LoginUseCase
import ua.morozova.popcorntime.domain.usecase.auth.LogoutUseCase
import ua.morozova.popcorntime.domain.usecase.validation.ValidateConfirmPasswordUseCase
import ua.morozova.popcorntime.domain.usecase.validation.ValidateEmailUseCase
import ua.morozova.popcorntime.domain.usecase.validation.ValidatePasswordUseCase
import ua.morozova.popcorntime.domain.usecase.validation.ValidateUsernameUseCase
import ua.morozova.popcorntime.ui.auth.AuthViewModel
import ua.morozova.popcorntime.ui.home.HomeViewModel
import ua.morozova.popcorntime.ui.onboarding.OnboardingViewModel
import ua.morozova.popcorntime.ui.register.RegisterViewModel

val appModule = module {
    single<Settings> { Settings() }
    single { AppSettings(get()) }
    single<OnboardingRepository> { OnboardingRepositoryImpl(get()) }

    single<SecureStorage> { AppSecureStorage(get()) }
    single<HttpClient> { createHttpClient() }
    single<AuthApiService> { AppAuthApiService(get()) }
    single { AuthorizedApiClient(get(), get(), get()) }
    single { ProfileApiService(get()) }
    single<AuthRepository> { AppAuthRepository(get(), get()) }
    single<ProfileRepository> { AppProfileRepository(get()) }

    factory { IsOnboardingCompletedUseCase(get()) }
    factory { SetOnboardingCompletedUseCase(get()) }
    factory { ValidateUsernameUseCase() }
    factory { ValidatePasswordUseCase() }
    factory { ValidateEmailUseCase() }
    factory { ValidateConfirmPasswordUseCase() }
    factory { LoginUseCase(get()) }
    factory { LogoutUseCase(get()) }
    factory { IsAuthorizedUseCase(get()) }
    factory { GetCurrentUserUseCase(get()) }

    viewModelOf(::MainViewModel)
    viewModelOf(::OnboardingViewModel)
    viewModelOf(::AuthViewModel)
    viewModelOf(::RegisterViewModel)
    viewModelOf(::HomeViewModel)
}