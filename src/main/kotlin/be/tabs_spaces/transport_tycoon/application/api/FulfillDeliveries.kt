package be.tabs_spaces.transport_tycoon.application.api

interface FulfillDeliveries {

    fun fulfill(rawPackages: String): Int

}
