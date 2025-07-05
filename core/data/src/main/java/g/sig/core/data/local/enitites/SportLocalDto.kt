package g.sig.core.data.local.enitites

import androidx.room.Entity
import androidx.room.PrimaryKey
import g.sig.core.domain.entities.Sport

@Entity(tableName = "sports")
data class SportLocalDto(
    @PrimaryKey
    val id: String,
    val name: String,
) {
    fun toDomain() =
        Sport(
            id = id,
            name = name,
            events = emptyList(),
        )
}
