package be.tabs_spaces.transport_tycoon.application.api

import be.tabs_spaces.transport_tycoon.application.domain.Packages

interface FulfillDeliveries {

    fun fulfill(packages: Packages): Int

}
