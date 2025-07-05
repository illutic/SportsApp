package g.sig.sportsapp.features.home

import g.sig.sportsapp.features.home.mvi.HomeViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val homeModule =
    module {
        viewModel {
            HomeViewModel(
                getSports = get(),
                updateFavoriteEvent = get(),
            )
        }
    }
