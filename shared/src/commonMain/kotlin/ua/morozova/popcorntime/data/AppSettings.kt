package ua.morozova.popcorntime.data

import com.russhwolf.settings.Settings

class AppSettings(private val settings: Settings) {

    var isOnboardingCompleted: Boolean
        get() = settings.getBoolean(KEY_ONBOARDING_COMPLETED, false)
        set(value) = settings.putBoolean(KEY_ONBOARDING_COMPLETED, value)

    private companion object {
        const val KEY_ONBOARDING_COMPLETED = "onboarding_completed"
    }
}