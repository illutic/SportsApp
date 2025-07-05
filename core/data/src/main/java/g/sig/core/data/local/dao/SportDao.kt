package g.sig.core.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import g.sig.core.data.local.enitites.SportLocalDto

@Dao
internal interface SportDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSport(sport: SportLocalDto)

    @Query("SELECT * FROM sports")
    suspend fun getSports(): List<SportLocalDto>
}
