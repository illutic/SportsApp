package g.sig.core.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import g.sig.core.data.local.enitites.EventLocalDto

@Dao
internal interface EventDao {
    @Query("SELECT * FROM events WHERE id = :eventId")
    suspend fun getEventById(eventId: String): EventLocalDto

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvent(event: EventLocalDto)

    @Query("SELECT * FROM events WHERE sportId = :sportId")
    suspend fun getEventsBySportId(sportId: String): List<EventLocalDto>
}
