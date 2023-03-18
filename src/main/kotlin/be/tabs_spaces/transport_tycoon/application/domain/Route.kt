package be.tabs_spaces.transport_tycoon.application.domain

data class Route(
    val from: Location,
    val to: Location,
    val duration: Int,
    val finalDestination: Location = to
)

data class Assignment(
    val cargo: Package,
    val route: Route,
)
