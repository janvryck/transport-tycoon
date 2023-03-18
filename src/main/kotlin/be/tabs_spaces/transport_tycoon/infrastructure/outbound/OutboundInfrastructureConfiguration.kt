package be.tabs_spaces.transport_tycoon.infrastructure.outbound

import be.tabs_spaces.transport_tycoon.application.domain.ports.outbound.RoutesRepository
import be.tabs_spaces.transport_tycoon.application.domain.ports.outbound.TransporterRepository

class OutboundInfrastructureConfiguration {

    val routes: RoutesRepository = InMemoryRoutes()
    val transporters: TransporterRepository = InMemoryTransporters()

}
