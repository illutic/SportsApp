package g.sig.sportsapp.features.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import g.sig.core.ui.ColorPalette
import g.sig.core.ui.Spacing
import g.sig.sportsapp.R
import g.sig.sportsapp.features.home.components.sportscard.SportCard
import g.sig.sportsapp.features.home.mvi.HomeEvent
import g.sig.sportsapp.features.home.mvi.HomeIntent
import g.sig.sportsapp.features.home.mvi.HomeState
import g.sig.sportsapp.features.home.mvi.HomeViewModel
import g.sig.sportsapp.features.home.mvi.SportUiItem
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HomeRoute(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = koinViewModel(),
) {
    val context = LocalContext.current
    val homeState = viewModel.homeState
    val homeEvent by viewModel.homeEvent.collectAsStateWithLifecycle(null)
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.sendIntent(HomeIntent.LoadSports)
    }

    LaunchedEffect(homeEvent) {
        val message =
            when (homeEvent) {
                HomeEvent.FailedToLoadSports -> context.getString(R.string.error_fetch_sports)
                HomeEvent.FailedToToggleFavoriteEvent -> context.getString(R.string.error_toggle_favorite)
                null -> return@LaunchedEffect
            }
        snackbarHostState.showSnackbar(message)
    }

    Scaffold(
        modifier = modifier,
        containerColor = ColorPalette.SportsGray,
        topBar = {
            TopAppBar(
                colors =
                    TopAppBarDefaults.topAppBarColors(
                        containerColor = ColorPalette.SportsBlue,
                        titleContentColor = ColorPalette.SportsWhite,
                    ),
                title = { Text(stringResource(R.string.app_name)) },
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) {
        HomeScreen(
            state = homeState,
            onIntent = viewModel::sendIntent,
            modifier =
                Modifier
                    .padding(it)
                    .padding(vertical = Spacing.padding_8),
                )
    }
}

@Composable
internal fun HomeScreen(
    state: HomeState,
    onIntent: (HomeIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = modifier, verticalArrangement = Arrangement.spacedBy(Spacing.padding_12)) {
        items(state.sportUiItems) { sportUiItem ->
            SportCard(
                state = sportUiItem,
                onSportFavorite = {
                    onIntent(
                        HomeIntent.ToggleFavoriteSport(
                            sportId = sportUiItem.id,
                            favorite = !sportUiItem.getFavoritesOnly,
                        ),
                    )
                },
                onSportExpand = {
                    onIntent(
                        HomeIntent.ToggleExpandSport(
                            sportId = sportUiItem.id,
                            isExpanded = !sportUiItem.isExpanded,
                        ),
                    )
                },
                onEventFavorite = { eventId, isFavorite ->
                    onIntent(
                        HomeIntent.ToggleFavoriteEvent(
                            eventId = eventId,
                            favorite = isFavorite,
                        ),
                    )
                },
            )
        }
    }
}

@Composable
@Preview
private fun HomeScreenPreview() {
    HomeScreen(
        state =
            HomeState().apply {
                isLoading = false
                sportUiItems =
                    listOf(
                        // Add some sample data for preview
                        SportUiItem(
                            id = "1",
                            name = "Football",
                        ),
                        SportUiItem(
                            id = "2",
                            name = "Basketball",
                        ),
                    )
            },
        onIntent = {},
    )
}
