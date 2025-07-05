package g.sig.core.data.repository

import g.sig.core.data.datasource.local.LocalEventDataSource
import g.sig.core.domain.entities.Event
import g.sig.core.domain.repository.EventRepository

internal class EventRepositoryImpl(
    private val localEventDataSource: LocalEventDataSource,
) : EventRepository {
    override suspend fun favoriteEvent(
        eventId: String,
        favorite: Boolean,
    ): Result<Event> =
        runCatching {
            val event =
                localEventDataSource.getEventById(eventId).getOrNull()
                    ?: error("Event with ID $eventId not found")

            val updatedEvent = event.copy(isFavorite = favorite)

            localEventDataSource
                .updateEvent(updatedEvent)
                .map { updatedEvent.toDomain() }
                .getOrThrow()
        }

    override suspend fun getEventsBySport(sportId: String): Result<List<Event>> =
        runCatching {
            localEventDataSource
                .getEventsBySport(sportId = sportId)
                .map { event -> event.toDomain() }
        }
}
