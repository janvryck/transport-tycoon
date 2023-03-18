package be.tabs_spaces.transport_tycoon.application.usecases

import be.tabs_spaces.transport_tycoon.Clock
import be.tabs_spaces.transport_tycoon.application.api.FulfillDeliveries
import be.tabs_spaces.transport_tycoon.application.domain.Assignment
import be.tabs_spaces.transport_tycoon.application.domain.Package
import be.tabs_spaces.transport_tycoon.application.domain.Packages
import be.tabs_spaces.transport_tycoon.application.domain.Transporter
import be.tabs_spaces.transport_tycoon.application.domain.ports.outbound.RoutesRepository
import be.tabs_spaces.transport_tycoon.application.domain.ports.outbound.TransporterRepository

class FulfillDeliveriesCommand(
    private val routesRepository: RoutesRepository,
    private val transporterRepository: TransporterRepository,
) : FulfillDeliveries {

    override fun fulfill(rawPackages: String): Int {
        val transporters = transporterRepository.findAll()
        val packages = Packages(rawPackages)

        while (!packages.delivered()) {
            packages.markAsDelivered()
            transporters.available()
                .forEach { transporter ->
                    findAssignment(transporter, packages)
                        ?.apply { transporter.assign(this) }
                }
            Clock.tick()
        }
        return packages.lastDelivery()
    }

    private fun findAssignment(
        transporter: Transporter,
        packages: Packages,
    ) = packages.getAvailablePackageAt(transporter.pickupLocation)
        ?.let { cargo -> createAssignment(cargo) }

    private fun createAssignment(cargo: Package) =
        Assignment(cargo, routesRepository.find(from = cargo.location, to = cargo.destination))

}
