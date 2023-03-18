package be.tabs_spaces.transport_tycoon.infrastructure.inbound

import be.tabs_spaces.transport_tycoon.application.api.FulfillDeliveries
import be.tabs_spaces.transport_tycoon.application.domain.Location.A
import be.tabs_spaces.transport_tycoon.application.domain.Location.B
import be.tabs_spaces.transport_tycoon.application.domain.Package
import be.tabs_spaces.transport_tycoon.application.domain.Packages
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class DeliveriesControllerTest {

    private var fulfilledDeliveries: Packages? = null
    private val fulfillDeliveriesFake = object : FulfillDeliveries {
        override fun fulfill(packages: Packages): Int {
            fulfilledDeliveries = packages
            return 0
        }
    }

    private val controller = DeliveriesController(fulfillDeliveriesFake)

    @Test
    fun `Should fulfill deliveries`() {
        val rawDeliveries = "ABBA"

        controller.fulfill(rawDeliveries)

        assertEquals(
            Packages(Package(A), Package(B), Package(B), Package(A)),
            fulfilledDeliveries
        )
    }

}
