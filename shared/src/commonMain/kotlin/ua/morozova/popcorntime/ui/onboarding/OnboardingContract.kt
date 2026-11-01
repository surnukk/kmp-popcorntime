package ua.morozova.popcorntime.ui.onboarding

object OnboardingContract {

    const val PAGE_COUNT = 3

    data class State(
        val currentPage: Int = 0,
        val pageCount: Int = PAGE_COUNT
    ) {
        val isFirstPage: Boolean get() = currentPage == 0
        val isLastPage: Boolean get() = currentPage == pageCount - 1
    }

    sealed interface Intent {
        data object NextClicked : Intent
        data object BackClicked : Intent
        data object SkipClicked : Intent
        data object SignUpClicked : Intent
        data class PageSelected(val page: Int) : Intent
        data class PageChanged(val page: Int) : Intent
    }

    sealed interface Effect {
        data object NavigateToAuth : Effect
    }
}