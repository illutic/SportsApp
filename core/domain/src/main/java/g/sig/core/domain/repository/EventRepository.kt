package g.sig.core.domain.repository

import g.sig.core.domain.entities.Event

interface EventRepository {
    /**
     * Favorites an event by its ID.
     * @param eventId The ID of the event to favorite.
     * @return The updated event after favoriting.
     */
    suspend fun favoriteEvent(
        eventId: String,
        favorite: Boolean,
    ): Result<Event>

    /**
     * Fetches the list of events for a specific sport.
     * @param sportId The ID of the sport to fetch events for.
     * @return List of events for the specified sport.
     */
    suspend fun getEventsBySport(sportId: String): Result<List<Event>>
}
