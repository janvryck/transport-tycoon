package be.tabs_spaces.transport_tycoon

class TransportTycoon {

    fun transport(input: String): Int {
        val packages = input
            .map { Location.valueOf(it.toString()) }
            .map { location -> Package(location) }
            .toList()
        val transporters = listOf(Transporter.truck(), Transporter.truck(), Transporter.boat())

        var tick = 0
        while (!packages.delivered()) {
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
            tick++
        }
        return packages.maxOf { it.arrivesAt ?: -1 }
    }

    private fun List<Package>.delivered() = all { it.arrived }
}
