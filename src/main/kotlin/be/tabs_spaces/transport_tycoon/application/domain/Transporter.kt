package be.tabs_spaces.transport_tycoon.application.domain

import be.tabs_spaces.transport_tycoon.Clock

data class Transporter(
    var availableAt: Int = 0,
    val pickupLocation: Location
) {
    companion object {
        fun truck() = Transporter(pickupLocation = Location.FACTORY)
        fun boat() = Transporter(pickupLocation = Location.PORT)
    }

    private fun onRoute(route: Route) {
        availableAt = Clock.tick + 2 * route.duration
    }

    fun assign(assignment: Assignment) {
        onRoute(assignment.route)
        assignment.cargo.onRoute(assignment.route)
    }
}

class Transporters(vararg transporters: Transporter) {
    private val transporters = transporters.toList()

    fun available() = transporters.filter { it.availableAt <= Clock.tick }

}
