package g.sig.core.data.datasource.local

import g.sig.core.data.async.AppDispatcher
import g.sig.core.data.local.SportsDatabase
import kotlinx.coroutines.CoroutineScope
import org.koin.core.qualifier.named
import org.koin.dsl.module

internal val localDataSourceModule =
    module {
        single {
            LocalEventDataSource(
                coroutineScope = CoroutineScope(get(named<AppDispatcher.IO>())),
                eventDao = get<SportsDatabase>().eventDao(),
            )
        }

        single {
            LocalSportDataSource(
                coroutineScope = CoroutineScope(get(named<AppDispatcher.IO>())),
                eventDao = get<SportsDatabase>().eventDao(),
                sportDao = get<SportsDatabase>().sportDao(),
            )
        }
    }
