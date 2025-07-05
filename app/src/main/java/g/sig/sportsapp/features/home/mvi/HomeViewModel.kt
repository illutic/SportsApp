package g.sig.sportsapp.features.home.mvi

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import g.sig.core.domain.usecase.FetchSportsUseCase
import g.sig.core.domain.usecase.UpdateFavoriteEventUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

internal class HomeViewModel(
    private val getSports: FetchSportsUseCase,
    private val updateFavoriteEvent: UpdateFavoriteEventUseCase,
) : ViewModel() {
    private val _homeEvent = MutableSharedFlow<HomeEvent>()
    val homeEvent = _homeEvent.asSharedFlow()
    val homeState = HomeState()

    fun sendIntent(intent: HomeIntent) {
        viewModelScope.launch {
            when (intent) {
                HomeIntent.LoadSports -> loadSports()
                is HomeIntent.ToggleExpandSport ->
                    toggleExpandSport(
                        sportId = intent.sportId,
                        isExpanded = intent.isExpanded,
                    )

                is HomeIntent.ToggleFavoriteEvent ->
                    toggleFavoriteEvent(
                        eventId = intent.eventId,
                        favorite = intent.favorite,
                    )

                is HomeIntent.ToggleFavoriteSport ->
                    toggleFavoriteSport(
                        sportId = intent.sportId,
                        favorite = intent.favorite,
                    )
            }
        }
    }

    private suspend fun loadSports() {
        homeState.isLoading = true
        getSports()
            .onSuccess {
                homeState.isLoading = false
                homeState.sportUiItems = it.map { sport -> sport.toUiItem() }
            }.onFailure {
                Log.e(TAG, "Failed to load sports", it)
                _homeEvent.emit(HomeEvent.FailedToLoadSports)
                homeState.isLoading = false
            }
    }

    private fun toggleExpandSport(
        sportId: String,
        isExpanded: Boolean,
    ) {
        val sport = homeState.sportUiItems.find { it.id == sportId } ?: return
        sport.isExpanded = isExpanded
    }

    private suspend fun toggleFavoriteEvent(
        eventId: String,
        favorite: Boolean,
    ) {
        updateFavoriteEvent(eventId, favorite)
            .onSuccess {
                val events = homeState.sportUiItems.flatMap { it.events }
                val event = events.find { it.id == eventId } ?: return@onSuccess
                event.isFavorite = favorite
            }.onFailure {
                Log.e(TAG, "Failed to toggle favorite event", it)
                _homeEvent.emit(HomeEvent.FailedToToggleFavoriteEvent)
            }
    }

    private fun toggleFavoriteSport(
        sportId: String,
        favorite: Boolean,
    ) {
        val sport = homeState.sportUiItems.find { it.id == sportId } ?: return
        sport.getFavoritesOnly = favorite
    }

    companion object {
        private const val TAG = "HomeViewModel"
    }
}
