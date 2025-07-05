package g.sig.core.data.remote.enitites

import g.sig.core.data.local.enitites.SportLocalDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SportApiDto(
    @SerialName("i")
    val id: String,
    @SerialName("d")
    val name: String,
    @SerialName("e")
    val events: List<EventApiDto>,
) {
    fun toLocal() =
        SportLocalDto(
            id = id,
            name = name,
        )
}
