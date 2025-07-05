package g.sig.sportsapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import g.sig.sportsapp.features.home.HomeRoute

@Composable
internal fun SportsNavHost(
    modifier: Modifier = Modifier,
    navHostController: NavHostController = rememberNavController(),
) {
    NavHost(
        modifier = modifier,
        navController = navHostController,
        startDestination = Route.Home.path,
    ) {
        composable(Route.Home.path) {
            HomeRoute()
        }
    }
}
