package g.sig.core.domain.usecase

import g.sig.core.domain.repository.EventRepository
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class UpdateFavoriteEventUseCase(
    private val coroutineContext: CoroutineContext,
    private val eventRepository: EventRepository,
) {
    suspend operator fun invoke(
        eventId: String,
        favorite: Boolean,
    ) = runCatching {
        withContext(coroutineContext) {
            eventRepository.favoriteEvent(eventId, favorite).getOrThrow()
        }
    }
}
