package g.sig.core.data.repository

import g.sig.core.data.datasource.local.LocalSportDataSource
import g.sig.core.data.datasource.remote.RemoteSportDataSource
import g.sig.core.domain.entities.Sport
import g.sig.core.domain.repository.SportRepository
import kotlinx.coroutines.flow.map

internal class SportRepositoryImpl(
    private val localSportDataSource: LocalSportDataSource,
    private val remoteSportDataSource: RemoteSportDataSource,
) : SportRepository {
    override suspend fun getSports(): Result<List<Sport>> =
        runCatching {
            remoteSportDataSource
                .getSports()
                .getOrNull()
                ?.forEach { sport ->
                    localSportDataSource.updateSport(sport).getOrThrow()
                }

            localSportDataSource
                .getSports()
                .map { sport -> sport.toDomain() }
        }
}
