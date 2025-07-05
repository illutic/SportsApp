package g.sig.core.data.repository

import g.sig.core.data.datasource.local.LocalEventDataSource
import g.sig.core.data.datasource.local.LocalFavoriteDataSource
import g.sig.core.data.local.dao.EventDao
import g.sig.core.data.local.dao.FavoriteDao
import g.sig.core.data.local.enitites.EventLocalDto
import g.sig.core.data.local.enitites.FavoriteLocalDto
import g.sig.core.domain.entities.Event
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test

class EventRepositoryImplTest {
    @Test
    fun `when game exists, then get all its events`() =
        runTest {
            val mockDao = mockk<EventDao>()
            val mockFavoriteDao = mockk<FavoriteDao>()
            val repository =
                EventRepositoryImpl(
                    localEventDataSource =
                        LocalEventDataSource(
                            coroutineScope = this,
                            eventDao = mockDao,
                        ),
                    localFavoriteDataSource =
                        LocalFavoriteDataSource(
                            coroutineScope = this,
                            favoriteDao = mockFavoriteDao,
                        ),
                )

            coEvery { mockDao.getEventsBySportId(any()) }
                .returns(
                    listOf(
                        EventLocalDto(
                            id = "1",
                            name = "Event 1",
                            sportId = "sport1",
                            startTime = 0,
                        ),
                        EventLocalDto(
                            id = "2",
                            name = "Event 2",
                            sportId = "sport1",
                            startTime = 0,
                        ),
                    ),
                )

            coEvery { mockFavoriteDao.getFavorite("2") } returns
                FavoriteLocalDto(
                    eventId = "2",
                )

            val expectedEvents =
                listOf(
                    Event(
                        id = "1",
                        name = "Event 1",
                        sportId = "sport1",
                        isFavorite = false,
                        startTime = 0,
                    ),
                    Event(
                        id = "2",
                        name = "Event 2",
                        sportId = "sport1",
                        isFavorite = true,
                        startTime = 0,
                    ),
                )
            val result = repository.getEventsBySport("sport1").getOrThrow()
            assertEquals(expectedEvents, result)
        }

    @Test
    fun `when game does not exist, then return empty list`() =
        runTest {
            val mockDao = mockk<EventDao>()
            val mockFavoriteDao = mockk<FavoriteDao>()
            val repository =
                EventRepositoryImpl(
                    localEventDataSource =
                        LocalEventDataSource(
                            coroutineScope = this,
                            eventDao = mockDao,
                        ),
                    localFavoriteDataSource =
                        LocalFavoriteDataSource(
                            coroutineScope = this,
                            favoriteDao = mockFavoriteDao,
                        ),
                )

            coEvery { mockDao.getEventsBySportId(any()) }
                .returns(emptyList())

            val result = repository.getEventsBySport("sport1").getOrThrow()
            assertEquals(emptyList<Event>(), result)
        }

    @Test
    fun `when favorite event, then update its favorite status`() =
        runTest {
            val mockDao = mockk<EventDao>()
            val mockFavoriteDao = mockk<FavoriteDao>()
            val repository =
                EventRepositoryImpl(
                    localEventDataSource =
                        LocalEventDataSource(
                            coroutineScope = this,
                            eventDao = mockDao,
                        ),
                    localFavoriteDataSource =
                        LocalFavoriteDataSource(
                            coroutineScope = this,
                            favoriteDao = mockFavoriteDao,
                        ),
                )

            val eventId = "1"
            val favorite = true

            coEvery { mockDao.getEventById("1") } returns
                EventLocalDto(
                    id = "1",
                    name = "Event 1",
                    sportId = "sport1",
                    startTime = 0,
                )
            coEvery {
                mockDao.insertEvent(
                    EventLocalDto(
                        id = "1",
                        name = "Event 1",
                        sportId = "sport1",
                        startTime = 0,
                    ),
                )
            } returns Unit

            coEvery { mockFavoriteDao.addFavorite(any()) } returns Unit

            val result = repository.favoriteEvent(eventId, favorite)
            val expectedEvent =
                Event(
                    id = "1",
                    name = "Event 1",
                    sportId = "sport1",
                    isFavorite = favorite,
                    startTime = 0,
                )

            assertEquals(Result.success(expectedEvent), result)
        }

    @Test
    fun `when favorite event does not exist, then return error`() =
        runTest {
            val mockDao = mockk<EventDao>()
            val mockFavoriteDao = mockk<FavoriteDao>()
            val repository =
                EventRepositoryImpl(
                    localEventDataSource =
                        LocalEventDataSource(
                            coroutineScope = this,
                            eventDao = mockDao,
                        ),
                    localFavoriteDataSource =
                        LocalFavoriteDataSource(
                            coroutineScope = this,
                            favoriteDao = mockFavoriteDao,
                        ),
                )

            val eventId = "1"
            val favorite = true

            val result = repository.favoriteEvent(eventId, favorite)
            assert(result.isFailure)
        }

    @Test
    fun `when favorite event with invalid data, then return error`() =
        runTest {
            val mockDao = mockk<EventDao>()
            val mockFavoriteDao = mockk<FavoriteDao>()
            val repository =
                EventRepositoryImpl(
                    localEventDataSource =
                        LocalEventDataSource(
                            coroutineScope = this,
                            eventDao = mockDao,
                        ),
                    localFavoriteDataSource =
                        LocalFavoriteDataSource(
                            coroutineScope = this,
                            favoriteDao = mockFavoriteDao,
                        ),
                )

            val eventId = "invalid"
            val favorite = true

            coEvery { mockDao.getEventById(eventId) } throws Throwable()

            val result = repository.favoriteEvent(eventId, favorite)
            assert(result.isFailure)
        }

    @Test
    fun `when getting events of favorite sport, then return only favorite events`() =
        runTest {
            val mockDao = mockk<EventDao>()
            val mockFavoriteDao = mockk<FavoriteDao>()
            val repository =
                EventRepositoryImpl(
                    localEventDataSource =
                        LocalEventDataSource(
                            coroutineScope = this,
                            eventDao = mockDao,
                        ),
                    localFavoriteDataSource =
                        LocalFavoriteDataSource(
                            coroutineScope = this,
                            favoriteDao = mockFavoriteDao,
                        ),
                )

            coEvery { mockDao.getEventsBySportId("sport1") }
                .returns(
                    listOf(
                        EventLocalDto(
                            id = "2",
                            name = "Event 2",
                            sportId = "sport1",
                            startTime = 0,
                        ),
                    ),
                )

            coEvery { mockFavoriteDao.getFavorite("2") } returns
                FavoriteLocalDto(
                    eventId = "2",
                )

            val expectedEvents =
                listOf(
                    Event(
                        id = "2",
                        name = "Event 2",
                        isFavorite = true,
                        startTime = 0,
                        sportId = "sport1",
                    ),
                )
            val result = repository.getEventsBySport("sport1").getOrThrow()
            assertEquals(expectedEvents, result)
        }
}
