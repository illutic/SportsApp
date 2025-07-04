package g.sig.core.domain.entities

data class Event(
    val id: String,
    val competitorA: String,
    val competitorB: String,
    val startTime: String,
    val isFavorite: Boolean,
)
