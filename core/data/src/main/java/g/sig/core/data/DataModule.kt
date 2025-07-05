package g.sig.core.data

import g.sig.core.data.async.AppDispatcher
import g.sig.core.data.datasource.local.localDataSourceModule
import g.sig.core.data.datasource.remote.remoteDataSourceModule
import g.sig.core.data.local.databaseModule
import g.sig.core.data.remote.ApiClient
import g.sig.core.data.remote.ApiClientImpl
import g.sig.core.data.remote.buildHttpClient
import g.sig.core.data.repository.eventRepositoryModule
import g.sig.core.data.repository.sportRepositoryModule
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.cio.CIO
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.core.module.dsl.named
import org.koin.core.module.dsl.withOptions
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dataModule =
    module {
        includes(databaseModule, dispatchersModule, httpModule, apiClientModule)
        includes(remoteDataSourceModule, localDataSourceModule)
        includes(eventRepositoryModule, sportRepositoryModule)
    }

internal val apiClientModule =
    module {
        single<ApiClient> {
            val dispatcher = get<CoroutineDispatcher>(named<AppDispatcher.IO>())

            ApiClientImpl(
                coroutineScope =
                    CoroutineScope(
                        dispatcher + CoroutineName("ApiClientScope"),
                    ),
                httpClient = get(),
            )
        }
    }

internal val httpModule =
    module {
        single<HttpClientEngine> { CIO.create() }
        single { buildHttpClient(get()) }
    }

internal val dispatchersModule =
    module {
        single { Dispatchers.IO } withOptions { named<AppDispatcher.IO>() }
        single { Dispatchers.Default } withOptions { named<AppDispatcher.Default>() }
        single { Dispatchers.Main } withOptions { named<AppDispatcher.Main>() }
    }
