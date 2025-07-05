package g.sig.core.data.serialization

import kotlinx.serialization.json.Json

internal val JsonSerializer =
    Json {
        ignoreUnknownKeys = true
        isLenient = true
        prettyPrint = true
    }
