import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import kotlin.test.assertEquals

class TransportTycoonTest {

    @CsvSource(
        value = [
            "BB,5",
            "BBB,15",
            "BBBBB,25",
//            "A,5",
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

    /**
     * A=1+4        -> 1 to port, 4 to B
     * AB=5         -> straight to B
     * AA=1+4+4+4   -> go to port in parallel -> boat goes from port to A twice + return
     * BB=5         -> straight
     */
    private fun transport(input: String): Int {
        val packets = input
            .map { Destination.valueOf(it.toString()) }
            .map { location -> Packet(location) }
            .toList()
        val trucks = listOf(Truck(), Truck())

        var tick = 0
        while (!packets.delivered()) {
            packets
                .filter { it.arrivesAt == tick }
                .forEach { it.arrived = true }

            trucks.filter { it.availableAt <= tick }
                .forEach { truck ->
                    val availablePacket = packets.firstOrNull { it.arrivesAt == null }

                    availablePacket?.apply {
                        when (destination) {
                            Destination.A -> {
                                arrivesAt = tick + 1
                                truck.availableAt = tick + 2 * 1
                            }

                            Destination.B -> {
                                arrivesAt = tick + 5
                                truck.availableAt = tick + 2 * 5
                            }

                            Destination.PORT -> {}
                        }
                    }

                }
            println(tick)
            println(packets)
            println(trucks)
            println("---")
            tick++
        }
        return packets.maxOf { it.arrivesAt ?: -1 }
    }

    private fun List<Packet>.delivered() = all { it.arrived }

    enum class Destination {
        A,
        B,
        PORT
    }

    data class Truck(
        var availableAt: Int = 0
    )

    data class Boat(
        val destination: Destination
    )

    data class Packet(
        val destination: Destination,
        var arrived: Boolean = false,
        var arrivesAt: Int? = null
    )
}

