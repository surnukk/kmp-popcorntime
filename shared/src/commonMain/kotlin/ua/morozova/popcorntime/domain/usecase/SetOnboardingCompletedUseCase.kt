package ua.morozova.popcorntime.domain.usecase

import ua.morozova.popcorntime.domain.OnboardingRepository

class SetOnboardingCompletedUseCase(
    private val repository: OnboardingRepository
) {
    operator fun invoke(completed: Boolean = true) {
        repository.setOnboardingCompleted(completed)
    }
}