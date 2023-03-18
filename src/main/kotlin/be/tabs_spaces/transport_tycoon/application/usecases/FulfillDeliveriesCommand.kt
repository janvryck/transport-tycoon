package be.tabs_spaces.transport_tycoon.application.usecases

import be.tabs_spaces.transport_tycoon.Clock
import be.tabs_spaces.transport_tycoon.application.api.FulfillDeliveries
import be.tabs_spaces.transport_tycoon.application.domain.*
import be.tabs_spaces.transport_tycoon.application.domain.Transporter.Companion.boat
import be.tabs_spaces.transport_tycoon.application.domain.Transporter.Companion.truck

class FulfillDeliveriesCommand : FulfillDeliveries {

    private val routes = Routes()

    override fun fulfill(input: String): Int {
        val packages = input
            .map { Location.valueOf(it.toString()) }
            .map { location -> Package(location) }
            .toList()
        val transporters = Transporters(truck(), truck(), boat())

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
            forEach { it.canArrive() }
    }
}
