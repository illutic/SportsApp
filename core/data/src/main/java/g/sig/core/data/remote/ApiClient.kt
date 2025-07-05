package g.sig.core.data.remote

import g.sig.core.data.remote.enitites.SportApiDto

internal interface ApiClient {
    suspend fun getSports(): Result<List<SportApiDto>>
}
