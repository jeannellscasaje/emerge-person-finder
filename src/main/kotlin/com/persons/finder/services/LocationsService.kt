package com.persons.finder.services

import com.persons.finder.domain.Location

interface LocationsService {
    fun addLocation(location: Location)
    fun removeLocation(locationReferenceId: Long)
    fun findAround(latitude: Double, longitude: Double, radiusInKm: Double) : List<Location>
}