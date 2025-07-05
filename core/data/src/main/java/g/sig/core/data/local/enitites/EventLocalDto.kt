package g.sig.core.data.local.enitites

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import g.sig.core.domain.entities.Event

@Entity(
    tableName = "events",
    foreignKeys = [
        ForeignKey(
            entity = SportLocalDto::class,
            parentColumns = ["id"],
            childColumns = ["sportId"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    indices = [
        Index("sportId"),
    ],
)
data class EventLocalDto(
    @PrimaryKey
    val id: String,
    val sportId: String,
    val name: String,
    val startTime: Long,
    val isFavorite: Boolean,
) {
    fun toDomain() =
        Event(
            id = id,
            sportId = sportId,
            name = name,
            startTime = startTime,
            isFavorite = isFavorite,
        )
}
