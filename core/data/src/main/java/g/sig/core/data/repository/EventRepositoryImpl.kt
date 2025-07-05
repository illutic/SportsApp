package g.sig.core.data.repository

import g.sig.core.data.datasource.local.LocalEventDataSource
import g.sig.core.data.datasource.local.LocalFavoriteDataSource
import g.sig.core.domain.entities.Event
import g.sig.core.domain.repository.EventRepository

internal class EventRepositoryImpl(
    private val localEventDataSource: LocalEventDataSource,
    private val localFavoriteDataSource: LocalFavoriteDataSource,
) : EventRepository {
    override suspend fun favoriteEvent(
        eventId: String,
        favorite: Boolean,
    ): Result<Event> =
        runCatching {
            val event =
                localEventDataSource.getEventById(eventId).getOrNull()
                    ?: error("Event with ID $eventId not found")

            if (favorite) {
                localFavoriteDataSource.addFavorite(eventId = eventId).getOrThrow()
            } else {
                localFavoriteDataSource.removeFavorite(eventId = eventId).getOrThrow()
            }

            event.toDomain().copy(isFavorite = favorite)
        }

    override suspend fun getEventsBySport(sportId: String): Result<List<Event>> =
        runCatching {
            localEventDataSource
                .getEventsBySport(sportId = sportId)
                .map { event ->
                    val isFavorite = localFavoriteDataSource.isFavorite(eventId = event.id).getOrElse { false }
                    event.toDomain().copy(isFavorite = isFavorite)
                }
        }
}
