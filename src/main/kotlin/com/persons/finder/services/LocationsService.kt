package com.persons.finder.services

import com.persons.finder.domain.Person
import com.persons.finder.dto.response.PersonsNearbyResponse

interface LocationsService {
    fun addLocation(person: Person)
    fun removeLocation(locationReferenceId: Long)
    fun findNearbyPersons(latitude: Double, longitude: Double, radiusInKm: Double): List<PersonsNearbyResponse>
}