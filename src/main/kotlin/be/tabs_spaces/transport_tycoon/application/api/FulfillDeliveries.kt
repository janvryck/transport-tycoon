package be.tabs_spaces.transport_tycoon.application.api

import be.tabs_spaces.transport_tycoon.application.domain.Packages

fun interface FulfillDeliveries {

    fun fulfill(packages: Packages): Int

}
