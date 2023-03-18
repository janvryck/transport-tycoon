package be.tabs_spaces.transport_tycoon.application.domain

import be.tabs_spaces.transport_tycoon.Clock

class Package(val destination: Location) {
    var location: Location = Location.FACTORY
        private set
    var arrivesAt: Int? = null
        private set
    var arrived: Boolean = false
        private set

    fun canArrive() {
        if(arrivesAt == Clock.tick && location == destination){
            arrived = true
        }

    }

    fun onRoute(route: Route) {
        arrivesAt = Clock.tick + route.duration
        location = route.to
    }
}

class Packages(rawListing: String) {
    val packages = rawListing
        .map { Location.valueOf(it.toString()) }
        .map { location -> Package(location) }
        .toList()

    fun getAvailablePackageAt(
        location: Location,
    ) = packages.filter { it.location == location }
        .firstOrNull { it.arrivesAt?.let { it <= Clock.tick } ?: true }

    fun delivered() = packages.all { it.arrived }

    fun markAsDeliveredAt() {
        packages.forEach { it.canArrive() }
    }

    fun lastDelivery() = packages.maxOf { it.arrivesAt ?: -1 }
}
