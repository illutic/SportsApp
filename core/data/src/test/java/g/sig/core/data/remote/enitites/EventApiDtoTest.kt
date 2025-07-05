package g.sig.core.data.remote.enitites

import junit.framework.TestCase.assertEquals
import org.junit.Test

class EventApiDtoTest {
    @Test
    fun `when converting to local dto, then start time is in milliseconds`() {
        val eventApiDto =
            EventApiDto(
                id = "1",
                sportId = "2",
                name = "Test Event",
                startTime = 1700000000, // Example timestamp in seconds
            )

        val localDto = eventApiDto.toLocal()

        assertEquals(1700000000L * 1000, localDto.startTime)
    }
}
