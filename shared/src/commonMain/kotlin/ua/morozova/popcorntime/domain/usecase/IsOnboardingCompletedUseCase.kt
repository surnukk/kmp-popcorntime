package ua.morozova.popcorntime.domain.usecase

import ua.morozova.popcorntime.domain.OnboardingRepository

class IsOnboardingCompletedUseCase(
    private val repository: OnboardingRepository
) {
    operator fun invoke(): Boolean = repository.isOnboardingCompleted()
}