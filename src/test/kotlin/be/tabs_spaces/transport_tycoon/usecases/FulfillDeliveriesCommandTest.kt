package be.tabs_spaces.transport_tycoon.usecases

import be.tabs_spaces.transport_tycoon.*
import be.tabs_spaces.transport_tycoon.application.usecases.FulfillDeliveriesCommand
import be.tabs_spaces.transport_tycoon.infrastructure.outbound.InMemoryRoutes
import be.tabs_spaces.transport_tycoon.infrastructure.outbound.InMemoryTransporters
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import kotlin.test.assertEquals

class FulfillDeliveriesCommandTest {

    private val command = FulfillDeliveriesCommand(
        InMemoryRoutes(),
        InMemoryTransporters()
    )

    @BeforeEach
    fun resetClock() {
        Clock.reset()
    }

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
        val result = command.fulfill(input)

        assertEquals(expected.toInt(), result)
    }

}

