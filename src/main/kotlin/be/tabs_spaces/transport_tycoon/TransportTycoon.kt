package be.tabs_spaces.transport_tycoon

class TransportTycoon() {

    fun transport(input: String): Int {
        val packages = input
            .map { Location.valueOf(it.toString()) }
            .map { location -> Package(location) }
            .toList()
        val transporters = listOf(Transporter.truck(), Transporter.truck(), Transporter.boat())

        var tick = 0
        while (!packages.delivered()) {
            packages.markAsDeliveredAt(tick)

            transporters.availableAt(tick)
                .forEach { transporter ->
                    val availablePackage = packages.getAvailablePackageAt(transporter.pickupLocation, tick)

                    availablePackage?.apply {
                        routes.find(from = location, to = destination)?.let { route ->
                            this.onRoute(route, tick)
                            transporter.onRoute(route, tick)
                        }
                    }

                }
            tick++
        }
        return packages.maxOf { it.arrivesAt ?: -1 }
    }

    private fun List<Package>.getAvailablePackageAt(
        location: Location,
        tick: Int
    ) = filter { it.location == location }
        .firstOrNull { it.arrivesAt?.let { it <= tick } ?: true }

    private fun List<Package>.delivered() = all { it.arrived }

    private fun List<Package>.markAsDeliveredAt(tick: Int) {
        filter { it.arrivesAt == tick }
            .filter { it.location == it.destination }
            .forEach {
                it.arrived = true
            }
    }

    private fun List<Transporter>.availableAt(tick: Int) = filter { it.availableAt <= tick }

    private fun List<Route>.find(from: Location, to: Location) = find { it.from == from && it.finalDestination == to }
}
