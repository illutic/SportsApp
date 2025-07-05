package g.sig.sportsapp.features.home.mvi

sealed interface HomeIntent {
    data object LoadSports : HomeIntent

    data class ToggleFavoriteSport(
        val sportId: String,
        val favorite: Boolean,
    ) : HomeIntent

    data class ToggleFavoriteEvent(
        val eventId: String,
        val favorite: Boolean,
    ) : HomeIntent

    data class ToggleExpandSport(
        val sportId: String,
        val isExpanded: Boolean = false,
    ) : HomeIntent
}
