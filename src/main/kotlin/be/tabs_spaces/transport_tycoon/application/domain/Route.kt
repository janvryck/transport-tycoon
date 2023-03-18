package be.tabs_spaces.transport_tycoon.application.domain

data class Route(
    val from: Location,
    val to: Location,
    val duration: Int,
    val finalDestination: Location = to
)

val routes = listOf(
    Route(Location.FACTORY, Location.PORT, 1, Location.A),
    Route(Location.PORT, Location.A, 4),
    Route(Location.FACTORY, Location.B, 5)
)
