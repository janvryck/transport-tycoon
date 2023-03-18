package be.tabs_spaces.transport_tycoon.infrastructure.inbound

import be.tabs_spaces.transport_tycoon.application.api.FulfillDeliveries
import be.tabs_spaces.transport_tycoon.application.domain.Location
import be.tabs_spaces.transport_tycoon.application.domain.Package
import be.tabs_spaces.transport_tycoon.application.domain.Packages
import kotlinx.cli.ArgType
import kotlinx.cli.ExperimentalCli
import kotlinx.cli.Subcommand

class DeliveriesController(
    private val fulfillDeliveries: FulfillDeliveries
) {

    companion object {
        const val command = "fulfill"
    }

    @OptIn(ExperimentalCli::class)
    fun subcommand(): Subcommand {
        return object : Subcommand(command, "Calculate the time it takes to fulfill deliveries") {
            val deliveries by argument(
                ArgType.String,
                "deliveries",
                "List of destinations",
            )

            override fun execute() {
                print(fulfill(deliveries))
            }

        }
    }

    fun fulfill(input: String) = input
        .map { Location.valueOf(it.toString()) }
        .map { location -> Package(location) }
        .toList()
        .let { Packages(it) }
        .let { fulfillDeliveries.fulfill(it) }

}
