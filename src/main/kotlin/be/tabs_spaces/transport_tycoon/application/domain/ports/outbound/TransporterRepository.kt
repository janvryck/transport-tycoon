package be.tabs_spaces.transport_tycoon.application.domain.ports.outbound

import be.tabs_spaces.transport_tycoon.application.domain.Transporters

interface TransporterRepository {

    fun findAll(): Transporters

}
