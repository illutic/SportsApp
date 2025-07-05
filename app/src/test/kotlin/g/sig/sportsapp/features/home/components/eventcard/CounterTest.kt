package g.sig.sportsapp.features.home.components.eventcard

import junit.framework.TestCase.assertEquals
import org.junit.Test

class CounterTest {
    @Test
    fun `converts millis to correct time format`() {
        val millis = 3661000L // 1 hour, 1 minute, and 1 second
        val expected = "01:01:01"
        val actual = getFormattedTimeOf(millis)
        assertEquals(expected, actual)
    }

    @Test
    fun `converts millis to correct time format with zero padding`() {
        val millis = 60000L // 1 minute
        val expected = "00:01:00"
        val actual = getFormattedTimeOf(millis)
        assertEquals(expected, actual)
    }

    @Test
    fun `converts millis to correct time format with only seconds`() {
        val millis = 5000L // 5 seconds
        val expected = "00:00:05"
        val actual = getFormattedTimeOf(millis)
        assertEquals(expected, actual)
    }

    @Test
    fun `converts millis to correct time format with zero hours`() {
        val millis = 3600000L // 1 hour
        val expected = "01:00:00"
        val actual = getFormattedTimeOf(millis)
        assertEquals(expected, actual)
    }

    @Test
    fun `converts millis to correct time format with zero minutes and seconds`() {
        val millis = 0L // Zero time
        val expected = "00:00:00"
        val actual = getFormattedTimeOf(millis)
        assertEquals(expected, actual)
    }

    @Test
    fun `converts millis to correct time format with large values`() {
        val millis = 86461000L // 1 day, 1 hour, 1 minute, and 1 second
        val expected = "24:01:01"
        val actual = getFormattedTimeOf(millis)
        assertEquals(expected, actual)
    }

    @Test
    fun `converts millis to correct time format with negative values`() {
        val millis = -5000L // Negative time
        val expected = "00:00:00"
        val actual = getFormattedTimeOf(millis)
        assertEquals(expected, actual)
    }
}
