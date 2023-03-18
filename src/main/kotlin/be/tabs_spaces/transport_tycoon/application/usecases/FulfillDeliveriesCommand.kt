package be.tabs_spaces.transport_tycoon.application.usecases

import be.tabs_spaces.transport_tycoon.Clock
import be.tabs_spaces.transport_tycoon.application.api.FulfillDeliveries
import be.tabs_spaces.transport_tycoon.application.domain.*
import be.tabs_spaces.transport_tycoon.application.domain.Transporter.Companion.boat
import be.tabs_spaces.transport_tycoon.application.domain.Transporter.Companion.truck

class FulfillDeliveriesCommand(packages: String) : FulfillDeliveries {

    private val routes = Routes()
    private val transporters = Transporters(truck(), truck(), boat())
    private val packages = Packages(packages)

    override fun fulfill(): Int {
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
        return packages.lastDelivery()
    }

}
