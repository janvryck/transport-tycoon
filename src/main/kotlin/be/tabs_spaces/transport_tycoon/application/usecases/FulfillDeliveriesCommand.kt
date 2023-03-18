package be.tabs_spaces.transport_tycoon.application.usecases

import be.tabs_spaces.transport_tycoon.Clock
import be.tabs_spaces.transport_tycoon.application.api.FulfillDeliveries
import be.tabs_spaces.transport_tycoon.application.domain.*

class FulfillDeliveriesCommand : FulfillDeliveries {

    override fun fulfill(input: String): Int {
        val packages = input
            .map { Location.valueOf(it.toString()) }
            .map { location -> Package(location) }
            .toList()
        val transporters = listOf(Transporter.truck(), Transporter.truck(), Transporter.boat())

        while (!packages.delivered()) {
            packages.markAsDeliveredAt()

            transporters.availableAt()
                .forEach { transporter ->
                    val availablePackage = packages.getAvailablePackageAt(transporter.pickupLocation)

                    availablePackage?.apply {
                        routes.find(from = location, to = destination)?.let { route ->
                            this.onRoute(route)
                            transporter.onRoute(route)
                        }
                    }

                }
            Clock.tick()
        }
        return packages.maxOf { it.arrivesAt ?: -1 }
    }

    private fun List<Package>.getAvailablePackageAt(
        location: Location,
    ) = filter { it.location == location }
        .firstOrNull { it.arrivesAt?.let { it <= Clock.tick } ?: true }

    private fun List<Package>.delivered() = all { it.arrived }

    private fun List<Package>.markAsDeliveredAt() {
            forEach {
                it.canArrive(Clock.tick)
            }
    }

    private fun List<Transporter>.availableAt() = filter { it.availableAt <= Clock.tick }

    private fun List<Route>.find(from: Location, to: Location) = find { it.from == from && it.finalDestination == to }
}
