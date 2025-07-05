package g.sig.sportsapp.features.home.components.sportscard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import g.sig.core.ui.Spacing
import g.sig.sportsapp.features.home.components.eventcard.EventCard
import g.sig.sportsapp.features.home.mvi.EventUiItem
import java.util.Calendar

private val EventCardWidth = 90.dp

@Composable
internal fun SportsCardEvents(
    eventUiItems: List<EventUiItem>,
    onFavorite: (id: String, isFavorite: Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    SportsCardEventsContent(
        eventUiItems = eventUiItems,
        onFavorite = onFavorite,
        modifier = modifier,
    )
}

@Composable
private fun SportsCardEventsContent(
    eventUiItems: List<EventUiItem>,
    onFavorite: (id: String, isFavorite: Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Adaptive(minSize = EventCardWidth),
        horizontalArrangement = Arrangement.spacedBy(Spacing.padding_8),
        verticalArrangement = Arrangement.spacedBy(Spacing.padding_8),
    ) {
        items(eventUiItems) { eventCardState ->
            EventCard(
                eventUiItem = eventCardState,
                onFavorite = onFavorite,
                modifier = Modifier.width(EventCardWidth),
            )
        }
    }
}

@Composable
@Preview
private fun SportsCardEventsGridPreview() {
    SportsCardEvents(
        eventUiItems =
            listOf(
                EventUiItem(
                    id = "1",
                    sportId = "1",
                    name = AnnotatedString("Event 1"),
                    startTime =
                        Calendar
                            .getInstance()
                            .apply {
                                add(Calendar.MINUTE, 30)
                            }.timeInMillis,
                ),
                EventUiItem(
                    id = "2",
                    sportId = "1",
                    name = AnnotatedString("Event 2"),
                    startTime = Calendar.getInstance().timeInMillis,
                ),
            ),
        onFavorite = { _, _ -> },
    )
}
