package g.sig.sportsapp.features.home.components.eventcard

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import g.sig.core.ui.ColorPalette
import g.sig.core.ui.Spacing
import kotlinx.coroutines.delay
import java.util.Calendar

private const val TICK_IN_MILLIS = 1000L

@Composable
internal fun Counter(
    startDateInMillis: Long,
    modifier: Modifier = Modifier,
) {
    var formatedDate by remember { mutableStateOf("00:00:00") }

    LaunchedEffect(startDateInMillis) {
        var timeDifference = startDateInMillis - System.currentTimeMillis()
        while (timeDifference > 0) {
            val seconds = (timeDifference / 1000) % 60
            val minutes = (timeDifference / (1000 * 60)) % 60
            val hours = (timeDifference / (1000 * 60 * 60))

            formatedDate = "%02d:%02d:%02d".format(hours, minutes, seconds)
            timeDifference -= TICK_IN_MILLIS
            delay(TICK_IN_MILLIS)
        }
    }

    Text(
        text = formatedDate,
        modifier =
            modifier
                .border(
                    width = 1.dp,
                    color = ColorPalette.SportsBlue,
                    shape = MaterialTheme.shapes.small,
                ).padding(Spacing.padding_4),
        style = MaterialTheme.typography.bodyLarge,
        color = ColorPalette.SportsWhite,
        textAlign = TextAlign.Center,
    )
}

@Composable
@Preview
private fun CounterPreview() {
    val startDate =
        remember {
            Calendar.getInstance().apply {
                add(Calendar.DAY_OF_WEEK, 2)
            }
        }
    Counter(
        startDateInMillis = startDate.timeInMillis,
    )
}
