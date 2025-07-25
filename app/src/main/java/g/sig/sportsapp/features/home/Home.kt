package g.sig.sportsapp.features.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
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
                    .padding(vertical = Spacing.padding_8)
                    .fillMaxSize(),
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HomeScreen(
    state: HomeState,
    onIntent: (HomeIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val pullToRefreshState = rememberPullToRefreshState()
    PullToRefreshBox(
        isRefreshing = state.isLoading,
        state = pullToRefreshState,
        indicator = {
            PullToRefreshDefaults.Indicator(
                state = pullToRefreshState,
                isRefreshing = state.isLoading,
                containerColor = ColorPalette.SportsWhite,
                color = ColorPalette.SportsBlue,
                modifier = Modifier.align(Alignment.TopCenter),
            )
        },
        onRefresh = { onIntent(HomeIntent.LoadSports) },
        modifier = modifier,
    ) {
        HomeContent(
            state = state,
            onIntent = onIntent,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
private fun HomeContent(
    state: HomeState,
    onIntent: (HomeIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(Spacing.padding_8),
    ) {
        if (state.isEmpty) {
            item {
                Text(
                    text = stringResource(R.string.no_sports),
                    color = ColorPalette.SportsWhite,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }
        items(state.sportUiItems) { sport ->
            SportCard(
                state = sport,
                onSportFavorite = {
                    onIntent(HomeIntent.ToggleFavoriteSport(it, !sport.getFavoritesOnly))
                },
                onSportExpand = {
                    onIntent(HomeIntent.ToggleExpandSport(it, !sport.isExpanded))
                },
                onEventFavorite = { id, isFavorite ->
                    onIntent(HomeIntent.ToggleFavoriteEvent(id, isFavorite))
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
