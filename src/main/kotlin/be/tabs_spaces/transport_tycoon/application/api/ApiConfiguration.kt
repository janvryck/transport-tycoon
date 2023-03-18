package be.tabs_spaces.transport_tycoon.application.api

import be.tabs_spaces.transport_tycoon.application.domain.ports.outbound.RoutesRepository
import be.tabs_spaces.transport_tycoon.application.domain.ports.outbound.TransporterRepository
import be.tabs_spaces.transport_tycoon.application.usecases.FulfillDeliveriesCommand

class ApiConfiguration {

    private lateinit var routes: RoutesRepository
    private lateinit var transporters: TransporterRepository

    val fulfillDeliveries by lazy { FulfillDeliveriesCommand(routes, transporters) }

    fun routes(routesProvider: () -> RoutesRepository) {
        this.routes = routesProvider()
    }

    fun transporters(transportersProvider: () -> TransporterRepository) {
        this.transporters = transportersProvider()
    }
}
