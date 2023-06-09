package be.tabs_spaces.transport_tycoon.application.domain

import be.tabs_spaces.transport_tycoon.Clock

data class Package(val destination: Location) {
    var location: Location = Location.FACTORY
        private set
    var arrivesAt: Int = -1
        private set
    var arrived: Boolean = false
        private set

    fun canArrive() {
        if (arrivesAt == Clock.tick && location == destination) {
            arrived = true
        }
    }

    fun onRoute(route: Route) {
        arrivesAt = Clock.tick + route.duration
        location = route.to
    }

}

data class Packages(private val packages: List<Package>) {

    constructor(vararg packages: Package): this(packages.toList())

    fun getAvailablePackageAt(
        location: Location,
    ) = packages.filter { it.location == location }
        .firstOrNull { it.arrivesAt <= Clock.tick }

    fun delivered() = packages.all { it.arrived }

    fun markAsDelivered() {
        packages.forEach { it.canArrive() }
    }

    fun lastDelivery() = packages.maxOf { it.arrivesAt }
}
