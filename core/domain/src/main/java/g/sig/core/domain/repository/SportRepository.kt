package g.sig.core.domain.repository

import g.sig.core.domain.entities.Sport
import kotlinx.coroutines.flow.Flow

interface SportRepository {
    /**
     * Fetches the list of sports.
     * @return Result containing a list of sports or an error.
     */
    suspend fun getSports(): Flow<List<Sport>>
}
