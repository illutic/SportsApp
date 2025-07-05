package g.sig.core.data.datasource.remote

import g.sig.core.data.remote.ApiClient
import kotlinx.coroutines.CoroutineScope

internal class RemoteSportDataSource(
    coroutineScope: CoroutineScope,
    private val apiClient: ApiClient,
) : CoroutineScope by coroutineScope {
    suspend fun getSports() = apiClient.getSports()
}
