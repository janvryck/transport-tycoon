import be.tabs_spaces.transport_tycoon.*
import be.tabs_spaces.transport_tycoon.Location.*
import be.tabs_spaces.transport_tycoon.Transporter.Companion.boat
import be.tabs_spaces.transport_tycoon.Transporter.Companion.truck
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import kotlin.test.assertEquals

class TransportTycoonTest {

    val tycoon = TransportTycoon()

    @CsvSource(
        value = [
            "A,5",
            "BB,5",
            "BBB,15",
            "BBBBB,25",
            "AB,5",
            "ABB,7",
            "AA,13",
            "AABABBAB,29",
            "ABBBABAAABBB,41"
        ]
    )
    @ParameterizedTest
    fun `Determines time to deliver all packages`(input: String, expected: String) {
        val result = tycoon.transport(input)

        assertEquals(expected.toInt(), result)
    }

}

