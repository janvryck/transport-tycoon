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
            packages.markAsDelivered()
            transporters.available()
                .forEach { transporter ->
                    findAssignment(transporter)
                        ?.apply { transporter.assign(this) }
                }
            Clock.tick()
        }
        return packages.lastDelivery()
    }

    private fun findAssignment(
        transporter: Transporter
    ) = packages.getAvailablePackageAt(transporter.pickupLocation)
        ?.let { cargo -> createAssignment(cargo) }

    private fun createAssignment(cargo: Package) =
        Assignment(cargo, routes.find(from = cargo.location, to = cargo.destination))

}
