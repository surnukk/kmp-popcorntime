package ua.morozova.popcorntime.ui.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ua.morozova.popcorntime.domain.usecase.SetOnboardingCompletedUseCase
import ua.morozova.popcorntime.ui.onboarding.OnboardingContract.Effect
import ua.morozova.popcorntime.ui.onboarding.OnboardingContract.Intent
import ua.morozova.popcorntime.ui.onboarding.OnboardingContract.State

class OnboardingViewModel(
    private val setOnboardingCompleted: SetOnboardingCompletedUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(State())
    val state: StateFlow<State> = _state.asStateFlow()

    private val _effect = Channel<Effect>(Channel.BUFFERED)
    val effect: Flow<Effect> = _effect.receiveAsFlow()

    fun onIntent(intent: Intent) {
        when (intent) {
            Intent.NextClicked -> {
                if (_state.value.isLastPage) finishOnboarding() else changePage(_state.value.currentPage + 1)
            }
            Intent.BackClicked -> changePage(_state.value.currentPage - 1)
            Intent.SkipClicked, Intent.SignUpClicked -> finishOnboarding()
            is Intent.PageSelected -> changePage(intent.page)
            is Intent.PageChanged -> changePage(intent.page)
        }
    }

    private fun changePage(page: Int) {
        _state.update { it.copy(currentPage = page.coerceIn(0, it.pageCount - 1)) }
    }

    private fun finishOnboarding() {
        viewModelScope.launch {
            setOnboardingCompleted(true)
            _effect.send(Effect.NavigateToAuth)
        }
    }
}