package g.sig.core.data.remote

import g.sig.core.data.remote.enitites.SportApiDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.CoroutineScope

internal class ApiClientImpl(
    coroutineScope: CoroutineScope,
    private val httpClient: HttpClient,
) : ApiClient,
    CoroutineScope by coroutineScope {
    override suspend fun getSports(): Result<List<SportApiDto>> =
        runCatching {
            httpClient.get("https://ios-kaizen.github.io/MockSports/sports.json").body()
        }
}
