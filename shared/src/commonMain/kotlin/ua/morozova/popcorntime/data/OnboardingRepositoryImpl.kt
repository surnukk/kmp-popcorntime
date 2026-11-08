package ua.morozova.popcorntime.data

import ua.morozova.popcorntime.domain.OnboardingRepository
import ua.morozova.popcorntime.data.AppSettings

class OnboardingRepositoryImpl(
    private val appSettings: AppSettings
) : OnboardingRepository {

    override fun isOnboardingCompleted(): Boolean = appSettings.isOnboardingCompleted

    override fun setOnboardingCompleted(completed: Boolean) {
        appSettings.isOnboardingCompleted = completed
    }
}