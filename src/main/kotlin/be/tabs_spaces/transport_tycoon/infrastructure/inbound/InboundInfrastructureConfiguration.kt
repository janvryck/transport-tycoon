package be.tabs_spaces.transport_tycoon.infrastructure.inbound

import be.tabs_spaces.transport_tycoon.application.api.FulfillDeliveries
import kotlinx.cli.ArgParser
import kotlinx.cli.ExperimentalCli

class InboundInfrastructureConfiguration(private val rootCommand: String) {

    private lateinit var fulfillDeliveries: FulfillDeliveries

    private val deliveriesController by lazy { DeliveriesController(fulfillDeliveries) }

    fun fulfillDeliveries(ucProvider: () -> FulfillDeliveries) {
        fulfillDeliveries = ucProvider()
    }

    @OptIn(ExperimentalCli::class)
    fun cli(): ArgParser {
        val parser = ArgParser(rootCommand)
        parser.subcommands(
            deliveriesController.subcommand()
        )
        return parser
    }

}
