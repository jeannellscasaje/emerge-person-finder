package com.persons.finder.services

import com.persons.finder.domain.Location
import org.springframework.stereotype.Service

@Service
class LocationsServiceImpl : LocationsService {

    override fun addLocation(location: Location) {
        TODO("Not yet implemented")
    }

    override fun removeLocation(locationReferenceId: Long) {
        TODO("Not yet implemented")
    }

    override fun findAround(latitude: Double, longitude: Double, radiusInKm: Double): List<Location> {
        TODO("Not yet implemented")
    }

}