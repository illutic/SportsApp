package g.sig.sportsapp.features.home.components.sportscard

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import g.sig.core.ui.ColorPalette
import g.sig.core.ui.Spacing

@Composable
internal fun SportCardHeader(
    name: String,
    isFavorite: Boolean,
    onFavorite: () -> Unit,
    isExpanded: Boolean,
    onExpand: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier =
            modifier
                .background(ColorPalette.SportsWhite)
                .clickable { onExpand() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        SportsCardHeaderTitle(
            name = name,
            modifier =
                Modifier
                    .weight(1f)
                    .padding(horizontal = Spacing.padding_8),
        )

        SportsCardHeaderActions(
            isFavorite = isFavorite,
            isExpanded = isExpanded,
            onFavorite = onFavorite,
        )
    }
}

@Composable
private fun SportsCardHeaderTitle(
    name: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Spacing.padding_8),
    ) {
        Box(
            modifier =
                Modifier
                    .background(ColorPalette.SportsRed, CircleShape)
                    .size(24.dp),
        )
        Text(
            text = name,
            style = MaterialTheme.typography.labelLarge,
            color = ColorPalette.SportsBlack,
        )
    }
}

@Composable
private fun SportsCardHeaderActions(
    isFavorite: Boolean,
    isExpanded: Boolean,
    onFavorite: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val chevronRotation by
        animateFloatAsState(
            targetValue = if (isExpanded) 180f else 0f,
            label = "Chevron Rotation",
        )

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Spacing.padding_8),
    ) {
        Switch(
            checked = isFavorite,
            onCheckedChange = { onFavorite() },
            thumbContent = {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = ColorPalette.SportsWhite,
                )
            },
            colors =
                SwitchDefaults.colors(
                    checkedThumbColor = ColorPalette.SportsBlue,
                    uncheckedThumbColor = ColorPalette.SportsGray,
                    checkedTrackColor = ColorPalette.SportsBlack,
                    uncheckedTrackColor = ColorPalette.SportsGray,
                ),
        )

        Icon(
            imageVector = Icons.Default.ArrowDropDown,
            contentDescription = null,
            tint = ColorPalette.SportsBlack,
            modifier = Modifier.rotate(chevronRotation),
        )
    }
}

@Composable
@Preview
private fun SportCardHeaderPreview() {
    SportCardHeader(
        name = "Football",
        isFavorite = false,
        onFavorite = {},
        isExpanded = false,
        onExpand = {},
    )
}
