package g.sig.core.data.datasource.local

import g.sig.core.data.local.dao.EventDao
import g.sig.core.data.local.enitites.EventLocalDto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

internal class LocalEventDataSource(
    coroutineScope: CoroutineScope,
    private val eventDao: EventDao,
) : CoroutineScope by coroutineScope {
    suspend fun getEventById(id: String): Result<EventLocalDto> =
        runCatching {
            eventDao.getEventById(id)
        }

    suspend fun updateEvent(event: EventLocalDto): Result<Unit> =
        runCatching {
            eventDao.insertEvent(event)
        }

    fun getEventsBySport(sportId: String): Flow<List<EventLocalDto>> = eventDao.getEventsBySportId(sportId)
}
