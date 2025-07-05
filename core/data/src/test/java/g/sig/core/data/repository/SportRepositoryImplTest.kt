package g.sig.core.data.repository

import g.sig.core.data.datasource.local.LocalSportDataSource
import g.sig.core.data.datasource.remote.RemoteSportDataSource
import g.sig.core.data.local.dao.EventDao
import g.sig.core.data.local.dao.SportDao
import g.sig.core.data.local.enitites.toLocal
import g.sig.core.data.remote.ApiClient
import g.sig.core.data.remote.enitites.EventApiDto
import g.sig.core.data.remote.enitites.SportApiDto
import g.sig.core.domain.entities.Sport
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test

class SportRepositoryImplTest {
    private val mockApiSportData =
        listOf(
            SportApiDto(
                id = "1",
                name = "Football",
                events =
                    listOf(
                        EventApiDto(
                            id = "1",
                            name = "Match 1",
                            sportId = "1",
                            startTime = 0,
                        ),
                    ),
            ),
            SportApiDto(
                id = "2",
                name = "Basketball",
                events =
                    listOf(
                        EventApiDto(
                            id = "2",
                            name = "Game 1",
                            sportId = "2",
                            startTime = 0,
                        ),
                    ),
            ),
        )

    private val mockLocalSportData = mockApiSportData.map { it.toLocal() }

    @Test
    fun `when getting all sports, then get sports from api and update local db`() =
        runTest {
            val mockEventDao = mockk<EventDao>()
            val mockSportDao = mockk<SportDao>()
            val mockSportApi = mockk<ApiClient>()
            val repository =
                SportRepositoryImpl(
                    localSportDataSource =
                        LocalSportDataSource(
                            coroutineScope = this,
                            eventDao = mockEventDao,
                            sportDao = mockSportDao,
                        ),
                    remoteSportDataSource =
                        RemoteSportDataSource(
                            coroutineScope = this,
                            apiClient = mockSportApi,
                        ),
                )

            // Suppose the local database has some sports
            coEvery { mockSportDao.getSports() } returns flowOf(mockLocalSportData)

            // Suppose the API returns the mock data
            coEvery { mockSportApi.getSports() } returns Result.success(mockApiSportData)

            // Suppose adding the sport events succeeds
            coEvery { mockEventDao.insertEvent(any()) } returns Unit

            // Suppose inserting sports into the local database succeeds
            coEvery { mockSportDao.insertSport(any()) } returns Unit

            val result = repository.getSports().first()

            val expected =
                listOf(
                    Sport(
                        id = "1",
                        name = "Football",
                    ),
                    Sport(
                        id = "2",
                        name = "Basketball",
                    ),
                )

            // Verify that the sports are inserted into the local database
            mockLocalSportData.forEach {
                coVerify { mockSportDao.insertSport(it) }
            }

            assertEquals(expected, result)
        }

    @Test
    fun `when getting sports, if api fails, then return local sports`() =
        runTest {
            val mockEventDao = mockk<EventDao>()
            val mockSportDao = mockk<SportDao>()
            val mockSportApi = mockk<ApiClient>()
            val repository =
                SportRepositoryImpl(
                    localSportDataSource =
                        LocalSportDataSource(
                            coroutineScope = this,
                            eventDao = mockEventDao,
                            sportDao = mockSportDao,
                        ),
                    remoteSportDataSource =
                        RemoteSportDataSource(
                            coroutineScope = this,
                            apiClient = mockSportApi,
                        ),
                )

            // Suppose the local database has some sports
            coEvery { mockSportDao.getSports() } returns flowOf(mockLocalSportData)

            // Suppose the API call fails
            coEvery { mockSportApi.getSports() } returns Result.failure(Exception("API Error"))

            val result = repository.getSports().first()

            assertEquals(mockLocalSportData.map { it.toDomain() }, result)
        }
}
