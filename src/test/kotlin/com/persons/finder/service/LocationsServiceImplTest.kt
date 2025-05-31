package com.persons.finder.service

import com.persons.finder.domain.Location
import com.persons.finder.domain.Person
import com.persons.finder.dto.response.PersonsNearbyResponse
import com.persons.finder.repository.LocationRepository
import com.persons.finder.repository.PersonRepository
import com.persons.finder.services.LocationsServiceImpl
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.kotlin.verify
import java.util.Optional
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class LocationsServiceImplTest {

    private val locationRepository = mock(LocationRepository::class.java)
    private val personRepository = mock(PersonRepository::class.java)
    private val service = LocationsServiceImpl(locationRepository, personRepository)

    @Test
    fun `addLocation should set location's person and save person`() {
        val person = Person(id = 1L, name = "John", location = Location(latitude = 1.0, longitude = 2.0))

        service.addLocation(person)

        assertEquals(person, person.location?.person)
        verify(personRepository).save(person)
    }

    @Test
    fun `removeLocation should nullify references and delete location`() {
        val person = Person(id = 1L, name = "Jane")
        val location = Location(id = 2L, latitude = 1.0, longitude = 2.0, person = person)
        person.location = location

        `when`(locationRepository.findById(2L)).thenReturn(Optional.of(location))

        service.removeLocation(2L)

        assertNull(location.person)
        assertNull(person.location)
        verify(locationRepository).deleteById(2L)
    }

    @Test
    fun `removeLocation should throw if location not found`() {
        `when`(locationRepository.findById(99L)).thenReturn(Optional.empty())

        val exception = assertThrows<NoSuchElementException> {
            service.removeLocation(99L)
        }

        assertEquals("Location with ID 99 not found", exception.message)
    }

    @Test
    fun `findNearbyPersons should return list of responses`() {
        val names = listOf("Alice", "Bob")
        `when`(locationRepository.findPersonNamesWithinRadiusSorted(1.0, 2.0, 5.0)).thenReturn(names)

        val result = service.findNearbyPersons(1.0, 2.0, 5.0)

        assertEquals(listOf(PersonsNearbyResponse("Alice"), PersonsNearbyResponse("Bob")), result)
    }
}