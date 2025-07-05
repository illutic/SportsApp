package g.sig.sportsapp.features.home.mvi

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

internal class HomeViewModel : ViewModel() {
    private val _homeState = MutableStateFlow(HomeState(isLoading = true))
    val homeState = _homeState.asStateFlow()

    fun sendIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.ToggleExpandSport -> TODO()
            is HomeIntent.ToggleFavoriteEvent -> TODO()
            is HomeIntent.ToggleFavoriteSport -> TODO()
        }
    }
}
