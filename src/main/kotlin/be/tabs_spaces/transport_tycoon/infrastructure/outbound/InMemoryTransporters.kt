package be.tabs_spaces.transport_tycoon.infrastructure.outbound

import be.tabs_spaces.transport_tycoon.application.domain.Transporter.Companion.boat
import be.tabs_spaces.transport_tycoon.application.domain.Transporter.Companion.truck
import be.tabs_spaces.transport_tycoon.application.domain.Transporters
import be.tabs_spaces.transport_tycoon.application.domain.ports.outbound.TransporterRepository

class InMemoryTransporters : TransporterRepository {
    override fun findAll() = Transporters(truck(), truck(), boat())

}
