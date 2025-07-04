package g.sig.core.domain.datasource

import g.sig.core.domain.entities.Event

interface EventDataSource {
    /**
     * Fetches the list of events.
     * @return List of events.
     */
    suspend fun getEvents(): Result<List<Long>>

    /**
     * Fetches the details of a specific event by its ID.
     * @param eventId The ID of the event to fetch.
     * @return The event details.
     */
    suspend fun getEventDetails(eventId: Long): Result<Long>

    /**
     * Fetches the list of events for a specific sport.
     * @param sportId The ID of the sport to fetch events for.
     * @return List of events for the specified sport.
     */
    suspend fun updateEvent(
        eventId: Long,
        event: Event,
    ): Result<Long>

    /**
     * Deletes an event by its ID.
     * @param eventId The ID of the event to delete.
     * @return Result indicating success or failure of the deletion.
     */
    suspend fun deleteEvent(eventId: Long): Result<Unit>
}
