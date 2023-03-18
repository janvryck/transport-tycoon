package be.tabs_spaces.transport_tycoon

data class Transporter(
    var availableAt: Int = 0,
    val pickupLocation: Location
) {
    companion object {
        fun truck() = Transporter(pickupLocation = Location.FACTORY)
        fun boat() = Transporter(pickupLocation = Location.PORT)
    }

    fun onRoute(route: Route) {
        availableAt = Clock.tick + 2 * route.duration
    }
}
