package g.sig.core.data

import androidx.room.Room
import g.sig.core.data.async.AppDispatcher
import g.sig.core.data.datasource.local.LocalEventDataSource
import g.sig.core.data.datasource.local.LocalFavoriteDataSource
import g.sig.core.data.datasource.local.LocalSportDataSource
import g.sig.core.data.datasource.remote.RemoteSportDataSource
import g.sig.core.data.local.SportsDatabase
import g.sig.core.data.remote.ApiClient
import g.sig.core.data.remote.ApiClientImpl
import g.sig.core.data.remote.buildHttpClient
import g.sig.core.data.repository.EventRepositoryImpl
import g.sig.core.data.repository.SportRepositoryImpl
import g.sig.core.domain.repository.EventRepository
import g.sig.core.domain.repository.SportRepository
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.cio.CIO
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.named
import org.koin.core.module.dsl.withOptions
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dataModule
    get() =
        module {
            includes(dispatchersModule)
            includes(databaseModule, httpModule, apiClientModule)
            includes(remoteDataSourceModule, localDataSourceModule)
            includes(eventRepositoryModule, sportRepositoryModule)
        }

internal val databaseModule =
    module {
        single {
            Room
                .databaseBuilder(
                    context = androidContext(),
                    klass = SportsDatabase::class.java,
                    name = "sports_database",
                ).build()
        }
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

internal val remoteDataSourceModule =
    module {
        single {
            RemoteSportDataSource(
                coroutineScope = CoroutineScope(get<CoroutineDispatcher>(named<AppDispatcher.IO>())),
                apiClient = get(),
            )
        }
    }

internal val localDataSourceModule =
    module {
        single {
            LocalEventDataSource(
                coroutineScope = CoroutineScope(get<CoroutineDispatcher>(named<AppDispatcher.IO>())),
                eventDao = get<SportsDatabase>().eventDao(),
            )
        }

        single {
            LocalSportDataSource(
                coroutineScope = CoroutineScope(get<CoroutineDispatcher>(named<AppDispatcher.IO>())),
                eventDao = get<SportsDatabase>().eventDao(),
                sportDao = get<SportsDatabase>().sportDao(),
            )
        }

        single {
            LocalFavoriteDataSource(
                coroutineScope = CoroutineScope(get<CoroutineDispatcher>(named<AppDispatcher.IO>())),
                favoriteDao = get<SportsDatabase>().favoriteDao(),
            )
        }
    }

internal val eventRepositoryModule =
    module {
        single<EventRepository> {
            EventRepositoryImpl(
                localEventDataSource = get(),
                localFavoriteDataSource = get(),
            )
        }
    }

internal val sportRepositoryModule =
    module {
        single<SportRepository> {
            SportRepositoryImpl(
                localSportDataSource = get(),
                remoteSportDataSource = get(),
            )
        }
    }
