package g.sig.core.domain.entities

data class Sport(
    val id: String,
    val name: String,
    val events: List<Event>,
)
