package g.sig.core.data.repository

import g.sig.core.domain.repository.EventRepository
import g.sig.core.domain.repository.SportRepository
import org.koin.dsl.module

internal val eventRepositoryModule =
    module {
        single<EventRepository> {
            EventRepositoryImpl(
                localEventDataSource = get(),
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
