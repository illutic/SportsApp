package g.sig.core.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import g.sig.core.data.local.enitites.FavoriteLocalDto

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(favoriteLocalDto: FavoriteLocalDto)

    @Query("SELECT * FROM favorites WHERE eventId = :eventId")
    suspend fun getFavorite(eventId: String): FavoriteLocalDto?

    @Query("DELETE FROM favorites WHERE eventId = :eventId")
    suspend fun removeFavorite(eventId: String)
}
