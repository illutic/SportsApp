package g.sig.core.data.repository

import g.sig.core.data.datasource.local.LocalSportDataSource
import g.sig.core.data.datasource.remote.RemoteSportDataSource
import g.sig.core.domain.entities.Sport
import g.sig.core.domain.repository.SportRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class SportRepositoryImpl(
    private val localSportDataSource: LocalSportDataSource,
    private val remoteSportDataSource: RemoteSportDataSource,
) : SportRepository {
    override suspend fun getSports(): Flow<List<Sport>> {
        remoteSportDataSource
            .getSports()
            .getOrNull()
            ?.forEach { sport ->
                localSportDataSource.updateSport(sport).getOrThrow()
            }

        return localSportDataSource
            .getSports()
            .map { sports -> sports.map { it.toDomain() } }
    }
}
