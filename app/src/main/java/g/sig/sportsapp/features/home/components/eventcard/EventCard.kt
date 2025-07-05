package g.sig.sportsapp.features.home.components.eventcard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import g.sig.core.ui.ColorPalette
import g.sig.core.ui.Spacing
import g.sig.sportsapp.features.home.mvi.EventUiItem
import java.util.Calendar

@Composable
internal fun EventCard(
    eventUiItem: EventUiItem,
    onFavorite: (id: String, isFavorite: Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(Spacing.padding_4),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Counter(
            startDateInMillis = eventUiItem.startTime,
        )
        FavoriteButton(
            isFavorite = eventUiItem.isFavorite,
            onClick = { onFavorite(eventUiItem.id, !eventUiItem.isFavorite) },
        )
        EventCardName(
            eventName = eventUiItem.name,
        )
    }
}

@Composable
private fun FavoriteButton(
    isFavorite: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    IconButton(
        onClick = onClick,
        modifier = modifier,
    ) {
        when {
            isFavorite ->
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Favorite",
                    tint = ColorPalette.SportsYellow,
                )

            else ->
                Icon(
                    imageVector = Icons.Outlined.Star,
                    contentDescription = "Not Favorite",
                    tint = Color.LightGray,
                )
        }
    }
}

@Composable
private fun EventCardName(
    eventName: AnnotatedString,
    modifier: Modifier = Modifier,
) {
    Text(
        text = eventName,
        style = MaterialTheme.typography.bodyLarge,
        textAlign = TextAlign.Center,
        modifier = modifier,
    )
}

@Composable
@Preview
private fun EventCardPreview() {
    EventCard(
        eventUiItem =
            EventUiItem(
                id = "1",
                sportId = "1",
                name = AnnotatedString("Team A\n vs \nTeam B"),
                startTime =
                    Calendar
                        .getInstance()
                        .apply {
                            add(Calendar.MINUTE, 30)
                        }.timeInMillis
            ),
        onFavorite = { _, _ -> },
    )
}
