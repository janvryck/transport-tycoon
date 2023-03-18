package be.tabs_spaces.transport_tycoon

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class TransportTycoonApplicationTest {

    @Test
    fun `Should reset clock`() {
        Clock.tick()
        assertEquals(1, Clock.tick)

        TransportTycoonApplication()

        assertEquals(0, Clock.tick)
    }

}
