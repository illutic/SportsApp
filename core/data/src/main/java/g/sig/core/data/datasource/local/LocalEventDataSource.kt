package g.sig.core.data.datasource.local

import g.sig.core.data.local.dao.EventDao
import g.sig.core.data.local.enitites.EventLocalDto
import kotlinx.coroutines.CoroutineScope

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

    suspend fun getEventsBySport(sportId: String): List<EventLocalDto> = eventDao.getEventsBySportId(sportId)
}
