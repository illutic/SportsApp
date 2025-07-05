package g.sig.sportsapp.features.home.mvi

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import g.sig.core.domain.entities.Event
import g.sig.core.domain.entities.Sport
import g.sig.core.ui.ColorPalette

internal class HomeState {
    var isLoading by mutableStateOf(false)
    var sportUiItems by mutableStateOf<List<SportUiItem>>(emptyList())
}

internal data class SportUiItem(
    val id: String,
    val name: String,
    val events: List<EventUiItem> = emptyList(),
) {
    var getFavoritesOnly by mutableStateOf(false)
    var isExpanded by mutableStateOf(false)
    val eventsToShow get() =
        if (getFavoritesOnly) {
            events.filter { it.isFavorite }
        } else {
            events
        }
}

internal data class EventUiItem(
    val id: String,
    val sportId: String,
    val name: AnnotatedString,
    val startTime: Long,
) {
    var isFavorite by mutableStateOf(false)
}

internal fun Sport.toUiItem() =
    SportUiItem(
        id = id,
        name = name,
        events = events.map { it.toUiItem() },
    )

internal fun Event.toUiItem() =
    EventUiItem(
        id = id,
        sportId = sportId,
        name =
            buildAnnotatedString {
                val teamA = name.split("-").getOrNull(0) ?: ""
                val teamB = name.split("-").getOrNull(1) ?: ""

                withStyle(SpanStyle(color = ColorPalette.SportsWhite)) {
                    append(teamA.trim())
                }
                withStyle(SpanStyle(color = ColorPalette.SportsRed)) {
                    append("\nvs\n")
                }
                withStyle(SpanStyle(color = ColorPalette.SportsWhite)) {
                    append(teamB.trim())
                }
            },
        startTime = startTime,
    ).apply {
        isFavorite = this@toUiItem.isFavorite
    }
