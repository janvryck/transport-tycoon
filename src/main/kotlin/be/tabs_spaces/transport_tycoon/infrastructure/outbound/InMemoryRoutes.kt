package be.tabs_spaces.transport_tycoon.infrastructure.outbound

import be.tabs_spaces.transport_tycoon.application.domain.Location
import be.tabs_spaces.transport_tycoon.application.domain.Location.*
import be.tabs_spaces.transport_tycoon.application.domain.Route
import be.tabs_spaces.transport_tycoon.application.domain.ports.outbound.RoutesRepository

class InMemoryRoutes : RoutesRepository {

    private val routes = listOf(
        Route(FACTORY, PORT, 1, A),
        Route(PORT, A, 4),
        Route(FACTORY, B, 5)
    )

    override fun find(from: Location, to: Location) = routes
        .find { it.from == from && it.finalDestination == to }
        ?: throw IllegalArgumentException("No path from $from to $to")
}
