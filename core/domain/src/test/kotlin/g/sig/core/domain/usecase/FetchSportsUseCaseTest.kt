package g.sig.core.domain.usecase

import g.sig.core.domain.entities.Event
import g.sig.core.domain.entities.Sport
import g.sig.core.domain.repository.EventRepository
import g.sig.core.domain.repository.SportRepository
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.test.runTest
import org.junit.Test

class FetchSportsUseCaseTest {
    @Test
    fun `when getting sports, then should return sports with events`() =
        runTest {
            val eventRepository = mockk<EventRepository>()
            val sportsRepository = mockk<SportRepository>()
            val useCase =
                FetchSportsUseCase(
                    coroutineContext = coroutineContext,
                    sportsRepository = sportsRepository,
                    eventsRepository = eventRepository,
                )

            coEvery { eventRepository.getEventsBySport(any()) } returns
                Result.success(
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
                    ),
                )
            coEvery { sportsRepository.getSports() } returns
                Result.success(
                    listOf(
                        Sport(
                            id = "sport1",
                            name = "Sport 1",
                            events = emptyList(),
                        ),
                        Sport(
                            id = "sport2",
                            name = "Sport 2",
                            events = emptyList(),
                        ),
                    ),
                )

            val result = useCase().getOrThrow()
            val expectedSports =
                listOf(
                    Sport(
                        id = "sport1",
                        name = "Sport 1",
                        events =
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
                            ),
                    ),
                    Sport(
                        id = "sport2",
                        name = "Sport 2",
                        events = emptyList(),
                    ),
                )
            TestCase.assertEquals(expectedSports, result)
        }

    @Test
    fun `when getting sports fails, then should return an error`() =
        runTest {
            val eventRepository = mockk<EventRepository>()
            val sportsRepository = mockk<SportRepository>()
            val useCase =
                FetchSportsUseCase(
                    coroutineContext = coroutineContext,
                    sportsRepository = sportsRepository,
                    eventsRepository = eventRepository,
                )

            coEvery { eventRepository.getEventsBySport(any()) } returns
                Result.failure(Exception("Failed to fetch events"))
            coEvery { sportsRepository.getSports() } returns
                Result.failure(Exception("Failed to fetch sports"))

            val result = useCase()
            assert(result.isFailure)
        }

    @Test
    fun `when getting sports with no events, then should return sports with empty events list`() =
        runTest {
            val eventRepository = mockk<EventRepository>()
            val sportsRepository = mockk<SportRepository>()
            val useCase =
                FetchSportsUseCase(
                    coroutineContext = coroutineContext,
                    sportsRepository = sportsRepository,
                    eventsRepository = eventRepository,
                )

            coEvery { eventRepository.getEventsBySport(any()) } returns Result.success(emptyList())
            coEvery { sportsRepository.getSports() } returns
                Result.success(
                    listOf(
                        Sport(
                            id = "sport1",
                            name = "Sport 1",
                            events = emptyList(),
                        ),
                    ),
                )

            val result = useCase().getOrThrow()
            val expectedSports =
                listOf(
                    Sport(
                        id = "sport1",
                        name = "Sport 1",
                        events = emptyList(),
                    ),
                )
            TestCase.assertEquals(expectedSports, result)
        }
}
