package g.sig.core.data.local.enitites

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "favorites",
    indices = [
        Index("eventId"),
    ],
)
data class FavoriteLocalDto(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val eventId: String,
)
