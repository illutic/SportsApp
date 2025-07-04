package g.sig.core.domain.datasource

import g.sig.core.domain.entities.Sport

interface SportDataSource {
    /**
     * Fetches the list of Sports.
     * @return List of Sports.
     */
    suspend fun getSports(): Result<List<Sport>>

    /**
     * Fetches the details of a specific Sport by its ID.
     * @param sportId The ID of the Sport to fetch.
     * @return The Sport details.
     */
    suspend fun getSportDetails(sportId: Long): Result<Sport>

    /**
     * Fetches the list of Sports for a specific sport.
     * @param sportId The ID of the sport to fetch Sports for.
     * @return List of Sports for the specified sport.
     */
    suspend fun updateSport(
        sportId: Long,
        sport: Sport,
    ): Result<Long>

    /**
     * Deletes a Sport by its ID.
     * @param sportId The ID of the Sport to delete.
     * @return Result indicating success or failure of the deletion.
     */
    suspend fun deleteSport(sportId: Long): Result<Unit>
}
