package be.tabs_spaces.transport_tycoon.infrastructure.inbound

import be.tabs_spaces.transport_tycoon.application.api.FulfillDeliveries
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class CliTest {

    private val standardOut = System.out
    private val outputStreamCaptor = ByteArrayOutputStream()
    private val rootCommand = "cmd"

    private var fulfillDeliveriesCalled = false
    private val cli = InboundInfrastructureConfiguration(rootCommand).apply {
        fulfillDeliveries {
            FulfillDeliveries {
                fulfillDeliveriesCalled = true
                1
            }
        }
    }.cli()

    @BeforeEach
    fun setUp() {
        System.setOut(PrintStream(outputStreamCaptor))
        fulfillDeliveriesCalled = false
    }

    @AfterEach
    fun tearDown() {
        System.setOut(standardOut)
    }

    @Test
    fun `Should call 'fulfill'`() {
        cli.parse(arrayOf(DeliveriesController.command, "AB"))

        assertEquals("1", outputStreamCaptor.toString())
        assertTrue(fulfillDeliveriesCalled)
    }
}
