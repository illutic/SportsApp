package g.sig.core.data.datasource.local

import g.sig.core.data.local.dao.EventDao
import g.sig.core.data.local.dao.SportDao
import g.sig.core.data.remote.enitites.SportApiDto
import kotlinx.coroutines.CoroutineScope

internal class LocalSportDataSource(
    coroutineScope: CoroutineScope,
    private val eventDao: EventDao,
    private val sportDao: SportDao,
) : CoroutineScope by coroutineScope {
    suspend fun updateSport(sport: SportApiDto): Result<Unit> =
        runCatching {
            sportDao.insertSport(sport.toLocal())
            sport.events.forEach {
                eventDao.insertEvent(it.toLocal())
            }
        }

    suspend fun getSports() = sportDao.getSports()
}
