package g.sig.core.data.local.enitites

import androidx.room.Entity
import androidx.room.ForeignKey
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
)
data class EventLocalDto(
    @PrimaryKey
    val id: String,
    val sportId: String,
    val name: String,
    val startTime: Int,
    val isFavorite: Boolean,
) {
    fun toDomain() =
        Event(
            id = id,
            name = name,
            startTime = startTime,
            isFavorite = isFavorite,
        )
}

fun Event.toLocal(sportId: String) =
    EventLocalDto(
        id = id,
        sportId = sportId,
        name = name,
        startTime = startTime,
        isFavorite = isFavorite,
    )
