package be.tabs_spaces.transport_tycoon

data class Package(
    val destination: Location,
    var location: Location = Location.FACTORY,
    var arrived: Boolean = false,
    var arrivesAt: Int? = null
)
