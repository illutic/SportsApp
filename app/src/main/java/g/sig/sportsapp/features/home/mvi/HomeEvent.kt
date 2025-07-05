package g.sig.sportsapp.features.home.mvi

sealed interface HomeEvent {
    data object FailedToLoadSports : HomeEvent

    data object FailedToToggleFavoriteEvent : HomeEvent
}
