package g.sig.core.domain.usecase

import g.sig.core.domain.repository.EventRepository
import g.sig.core.domain.repository.SportRepository
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class FetchSportsUseCase(
    private val coroutineContext: CoroutineContext,
    private val sportsRepository: SportRepository,
    private val eventsRepository: EventRepository,
) {
    suspend operator fun invoke() =
        runCatching {
            withContext(coroutineContext) {
                sportsRepository
                    .getSports()
                    .getOrThrow()
                    .map { sport ->
                        val events =
                            eventsRepository
                                .getEventsBySport(sport.id)
                                .getOrThrow()
                                .filter { it.sportId == sport.id }

                        sport.copy(events = events)
                    }
            }
        }
}
