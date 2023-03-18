package be.tabs_spaces.transport_tycoon

import be.tabs_spaces.transport_tycoon.application.api.ApiConfiguration
import be.tabs_spaces.transport_tycoon.infrastructure.inbound.InboundInfrastructureConfiguration
import be.tabs_spaces.transport_tycoon.infrastructure.outbound.OutboundInfrastructureConfiguration

class TransportTycoonApplication {

    private lateinit var commandName: String
    private lateinit var outboundInfra: OutboundInfrastructureConfiguration
    private lateinit var api: ApiConfiguration
    private lateinit var inboundInfra: InboundInfrastructureConfiguration

    init {
        initialize {
            commandName = "transport-tycoon"
            usecases {
                routes { outboundInfra.routes }
                transporters { outboundInfra.transporters }
            }
            inbound {
                fulfillDeliveries { api.fulfillDeliveries }
            }
        }
    }

    private fun initialize(init: TransportTycoonApplication.() -> Unit) {
        Clock.reset()
        outboundInfra = OutboundInfrastructureConfiguration()

        init()
    }

    private fun usecases(init: ApiConfiguration.() -> Unit) {
        api = ApiConfiguration()
        api.init()
    }

    private fun inbound(init: InboundInfrastructureConfiguration.() -> Unit) {
        inboundInfra = InboundInfrastructureConfiguration(commandName)
        inboundInfra.init()
    }

    fun cli() = inboundInfra.cli()
}

fun main(args: Array<String>) {
    TransportTycoonApplication().cli().parse(args)
}
