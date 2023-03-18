import TransportTycoonTest.Location.*
import TransportTycoonTest.Transporter.Companion.boat
import TransportTycoonTest.Transporter.Companion.truck
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import kotlin.test.assertEquals

class TransportTycoonTest {

    @CsvSource(
        value = [
            "A,5",
            "BB,5",
            "BBB,15",
            "BBBBB,25",
            "AB,5",
            "ABB,7",
            "AA,13"
        ]
    )
    @ParameterizedTest
    fun `return the correct output`(input: String, expected: String) {
        val result = transport(input)

        assertEquals(expected.toInt(), result)
    }

    private fun transport(input: String): Int {
        val packages = input
            .map { Location.valueOf(it.toString()) }
            .map { location -> Package(location) }
            .toList()
        val transporters = listOf(truck(), truck(), boat())

        var tick = 0
        while (!packages.delivered() && tick <= 25) {
            packages
                .filter { it.arrivesAt == tick }
                .filter { it.location == it.destination }
                .forEach {
                    it.arrived = true
                }

            transporters
                .filter { it.availableAt <= tick }
                .forEach { transporter ->
                    val availablePackage = packages
                        .filter { it.location == transporter.pickupLocation }
                        .firstOrNull { it.arrivesAt?.let { it <= tick } ?: true }

                    availablePackage?.apply {
                        routes.find { it.from == location && it.finalDestination == destination }
                            ?.run {
                                arrivesAt = tick + duration
                                location = to
                                transporter.availableAt = tick + 2 * duration
                            }
                    }

                }
            println(tick)
            println(packages)
            println(transporters)
            println("---")
            tick++
        }
        return packages.maxOf { it.arrivesAt ?: -1 }
    }

    private fun List<Package>.delivered() = all { it.arrived }

    enum class Location {
        A,
        B,
        PORT,
        FACTORY
        ;
    }

    val routes = listOf(
        Route(FACTORY, PORT, 1, A),
        Route(PORT, A, 4),
        Route(FACTORY, B, 5)
    )

    data class Route(
        val from: Location,
        val to: Location,
        val duration: Int,
        val finalDestination: Location = to
    )

    data class Transporter(
        var availableAt: Int = 0,
        val pickupLocation: Location
    ) {
        companion object {
            fun truck() = Transporter(pickupLocation = FACTORY)
            fun boat() = Transporter(pickupLocation = PORT)
        }
    }

    data class Package(
        val destination: Location,
        var location: Location = FACTORY,
        var arrived: Boolean = false,
        var arrivesAt: Int? = null
    )
}

