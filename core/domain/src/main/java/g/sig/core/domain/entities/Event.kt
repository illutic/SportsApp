package g.sig.core.domain.entities

data class Event(
    val id: String,
    val name: String,
    val startTime: Int,
    val isFavorite: Boolean,
)
