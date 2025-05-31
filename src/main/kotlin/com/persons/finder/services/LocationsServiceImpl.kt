package com.persons.finder.services

import com.persons.finder.domain.Person
import com.persons.finder.dto.response.PersonsNearbyResponse
import com.persons.finder.repository.LocationRepository
import com.persons.finder.repository.PersonRepository
import org.springframework.stereotype.Service

@Service
class LocationsServiceImpl(private val locationRepository: LocationRepository,
    private val personRepository: PersonRepository) : LocationsService {

    override fun addLocation(person: Person) {
        person.location?.person = person
        personRepository.save(person)
    }

    override fun removeLocation(locationReferenceId: Long) {
        val location = locationRepository.findById(locationReferenceId).orElseThrow {
            NoSuchElementException("Location with ID $locationReferenceId not found")
        }

        location.person?.location = null
        location.person = null

        locationRepository.deleteById(locationReferenceId)
    }

    override fun findNearbyPersons(latitude: Double, longitude: Double, radiusInKm: Double): List<PersonsNearbyResponse> {
        return locationRepository.findPersonNamesWithinRadiusSorted(latitude, longitude, radiusInKm)
            .map { name -> PersonsNearbyResponse(name) }
    }

}