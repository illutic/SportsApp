package g.sig.sportsapp.usecase

import g.sig.core.data.async.AppDispatcher
import g.sig.core.domain.usecase.FetchSportsUseCase
import g.sig.core.domain.usecase.UpdateFavoriteEventUseCase
import kotlinx.coroutines.CoroutineDispatcher
import org.koin.core.qualifier.named
import org.koin.dsl.module

val useCaseModule =
    module {
        single {
            FetchSportsUseCase(
                coroutineContext = get<CoroutineDispatcher>(named<AppDispatcher.Default>()),
                sportsRepository = get(),
                eventsRepository = get(),
            )
        }
        single {
            UpdateFavoriteEventUseCase(
                coroutineContext = get<CoroutineDispatcher>(named<AppDispatcher.Default>()),
                eventRepository = get(),
            )
        }
    }
