package g.sig.sportsapp.features.home.components.sportscard

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import g.sig.core.ui.ColorPalette
import g.sig.core.ui.Spacing
import g.sig.sportsapp.features.home.mvi.EventUiItem
import g.sig.sportsapp.features.home.mvi.SportUiItem
import java.util.Calendar

private val EventsListHeight = 400.dp

@Composable
internal fun SportCard(
    state: SportUiItem,
    onSportFavorite: (id: String) -> Unit,
    onSportExpand: (id: String) -> Unit,
    onEventFavorite: (id: String, isFavorite: Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier.animateContentSize()) {
        SportCardHeader(
            name = state.name,
            isFavorite = state.getFavoritesOnly,
            isExpanded = state.isExpanded,
            onFavorite = { onSportFavorite(state.id) },
            onExpand = { onSportExpand(state.id) },
        )

        if(state.isExpanded) {
            SportsCardEvents(
                eventUiItems = state.eventsToShow,
                onFavorite = onEventFavorite,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(ColorPalette.SportsGray)
                    .padding(vertical = Spacing.padding_8)
                    .heightIn(max = EventsListHeight),
            )
        }
    }
}

@Composable
@Preview
internal fun SportCardPreview() {
    SportCard(
        state =
            SportUiItem(
                id = "1",
                name = "Football",
                events =
                    listOf(
                        EventUiItem(
                            id = "1",
                            sportId = "1",
                            name = AnnotatedString("Team A\nvs\nTeam B"),
                            startTime = Calendar.getInstance().timeInMillis,
                        ),
                        EventUiItem(
                            id = "2",
                            sportId = "1",
                            name = AnnotatedString("Team C\nvs\nTeam D"),
                            startTime = Calendar.getInstance().timeInMillis + 3600000, // 1 hour later
                        ),
                    ),
            ),
        onSportFavorite = {},
        onSportExpand = {},
        onEventFavorite = { _, _ -> },
    )
}
