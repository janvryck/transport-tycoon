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
//            "AB,5",
//            "ABB,7",
//            "AA,13"
        ]
    )
    @ParameterizedTest
    fun `return the correct output`(input: String, expected: String) {
        val result = transport(input)

        assertEquals(expected.toInt(), result)
    }

    private fun transport(input: String): Int {
        val packets = input
            .map { Location.valueOf(it.toString()) }
            .map { location -> Packet(location) }
            .toList()
        val transporters = listOf(truck(), truck(), boat())

        var tick = 0
        while (!packets.delivered() && tick <= 25) {
            packets
                .filter { it.arrivesAt == tick }
                .filter { it.location == it.destination }
                .forEach {
                    it.arrived = true
                }

            transporters
                .filter { it.availableAt <= tick }
                .forEach { transporter ->
                    val availablePacket = packets
                        .filter { it.location == transporter.pickupLocation }
                        .firstOrNull { it.arrivesAt?.let { it <= tick } ?: true }

                    availablePacket?.apply {
                        when (destination) {
                            A -> {
                                arrivesAt = tick + 1
                                location = PORT
                                transporter.availableAt = tick + 2 * 1
                            }

                            B -> {
                                arrivesAt = tick + 5
                                location = destination
                                transporter.availableAt = tick + 2 * 5
                            }
                            PORT -> {
                                arrivesAt = tick + 4
                                location = A
                                transporter.availableAt = tick + 2 * 4
                            }
                            FACTORY -> {}
                        }
                    }

                }
            println(tick)
            println(packets)
            println(transporters)
            println("---")
            tick++
        }
        return packets.maxOf { it.arrivesAt ?: -1 }
    }

    private fun List<Packet>.delivered() = all { it.arrived }

    enum class Location {
        A,
        B,
        PORT,
        FACTORY
    }

    data class Transporter(
        var availableAt: Int = 0,
        val pickupLocation: Location
    ) {
        companion object {
            fun truck() = Transporter(pickupLocation = FACTORY)
            fun boat() = Transporter(pickupLocation = PORT)
        }
    }

    data class Packet(
        val destination: Location,
        var location: Location = FACTORY,
        var arrived: Boolean = false,
        var arrivesAt: Int? = null
    )
}

