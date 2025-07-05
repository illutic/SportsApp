package g.sig.core.data.remote.enitites

import g.sig.core.data.local.enitites.EventLocalDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EventApiDto(
    @SerialName("i")
    val id: String,
    @SerialName("si")
    val sportId: String,
    @SerialName("d")
    val name: String,
    @SerialName("tt")
    val startTime: Int,
) {
    fun toLocal() =
        EventLocalDto(
            id = id,
            sportId = sportId,
            name = name,
            startTime = startTime.toLong() * 1000,
            isFavorite = false,
        )
}
