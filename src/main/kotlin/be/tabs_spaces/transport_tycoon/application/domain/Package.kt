package be.tabs_spaces.transport_tycoon.application.domain

import be.tabs_spaces.transport_tycoon.Clock

class Package(val destination: Location) {
    var location: Location = Location.FACTORY
        private set
    var arrivesAt: Int? = null
        private set
    var arrived: Boolean = false
        private set

    fun canArrive(tick: Int) {
        if(arrivesAt == tick && location == destination){
            arrived = true
        }

    }

    fun onRoute(route: Route) {
        arrivesAt = Clock.tick + route.duration
        location = route.to
    }
}
