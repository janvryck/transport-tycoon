package be.tabs_spaces.transport_tycoon

object Clock {
    var tick = 0
        private set

    fun tick() {
        tick++
    }

    fun reset() {
        tick = 0
    }
}
