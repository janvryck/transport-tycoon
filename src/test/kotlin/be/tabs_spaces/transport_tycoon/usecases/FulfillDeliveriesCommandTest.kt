package be.tabs_spaces.transport_tycoon.usecases

import be.tabs_spaces.transport_tycoon.Clock
import be.tabs_spaces.transport_tycoon.application.domain.Location
import be.tabs_spaces.transport_tycoon.application.domain.Location.A
import be.tabs_spaces.transport_tycoon.application.domain.Location.B
import be.tabs_spaces.transport_tycoon.application.domain.Package
import be.tabs_spaces.transport_tycoon.application.domain.Packages
import be.tabs_spaces.transport_tycoon.application.usecases.FulfillDeliveriesCommand
import be.tabs_spaces.transport_tycoon.infrastructure.outbound.InMemoryRoutes
import be.tabs_spaces.transport_tycoon.infrastructure.outbound.InMemoryTransporters
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.MethodSource
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

    @MethodSource("testCases")
    @ParameterizedTest
    fun `Determines time to deliver all packages`(input: List<Package>, expected: Int) {
        val packages = Packages(input)

        val result = command.fulfill(packages)

        assertEquals(expected, result)
    }

    companion object {
        @JvmStatic
        fun testCases() = listOf(
            listOf(Package(A)) to 5,
            listOf(Package(B),Package(B)) to 5,
            listOf(Package(B),Package(B),Package(B)) to 15,
            listOf(Package(B),Package(B),Package(B),Package(B),Package(B)) to 25,
            listOf(Package(A),Package(B)) to 5,
            listOf(Package(A),Package(B),Package(B)) to 7,
            listOf(Package(A),Package(A)) to 13,
            listOf(Package(A),Package(A),Package(B),Package(A),Package(B),Package(B),Package(A),Package(B)) to 29,
            listOf(Package(A),Package(B),Package(B),Package(B),Package(A),Package(B),Package(A),Package(A),Package(A),Package(B),Package(B),Package(B)) to 41,
        ).map {
            (packages, duration) -> arguments(packages, duration)
        }.stream()
    }
}

