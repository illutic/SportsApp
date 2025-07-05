package g.sig.core.data.datasource.remote

import g.sig.core.data.async.AppDispatcher
import kotlinx.coroutines.CoroutineScope
import org.koin.core.qualifier.named
import org.koin.dsl.module

internal val remoteDataSourceModule =
    module {
        single {
            RemoteSportDataSource(
                coroutineScope = CoroutineScope(get(named<AppDispatcher.IO>())),
                apiClient = get(),
            )
        }
    }
