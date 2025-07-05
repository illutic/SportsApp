package g.sig.core.data.datasource.local

import g.sig.core.data.local.dao.FavoriteDao
import g.sig.core.data.local.enitites.FavoriteLocalDto
import kotlinx.coroutines.CoroutineScope

class LocalFavoriteDataSource(
    coroutineScope: CoroutineScope,
    private val favoriteDao: FavoriteDao,
) : CoroutineScope by coroutineScope {
    suspend fun addFavorite(eventId: String): Result<Unit> =
        runCatching {
            favoriteDao.addFavorite(FavoriteLocalDto(eventId = eventId))
        }

    suspend fun removeFavorite(eventId: String): Result<Unit> =
        runCatching {
            favoriteDao.removeFavorite(eventId)
        }

    suspend fun isFavorite(eventId: String): Result<Boolean> =
        runCatching {
            favoriteDao.getFavorite(eventId) != null
        }
}
