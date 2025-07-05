package g.sig.core.domain.repository

import g.sig.core.domain.entities.Sport

interface SportRepository {
    /**
     * Fetches the list of sports.
     * @return Result containing a list of sports or an error.
     */
    suspend fun getSports(): Result<List<Sport>>
}
