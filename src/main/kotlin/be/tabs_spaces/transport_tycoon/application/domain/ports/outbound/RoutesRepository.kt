package be.tabs_spaces.transport_tycoon.application.domain.ports.outbound

import be.tabs_spaces.transport_tycoon.application.domain.Location
import be.tabs_spaces.transport_tycoon.application.domain.Route

interface RoutesRepository {
    fun find(from: Location, to: Location): Route
}
