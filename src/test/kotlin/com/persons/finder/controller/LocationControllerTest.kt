package com.persons.finder.controller

import com.persons.finder.domain.Person
import com.persons.finder.dto.PersonDto
import com.persons.finder.dto.request.LocationUpdateRequest
import com.persons.finder.services.LocationsService
import com.persons.finder.services.PersonsService
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.kotlin.verify
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import kotlin.test.Test

class LocationControllerTest {

    private val personsService = mock(PersonsService::class.java)
    private val locationsService = mock(LocationsService::class.java)

    private val controller = LocationController(personsService, locationsService)

    @Test
    fun `updateLocation should update person's location and return accepted`() {

        val personId = 1L
        val request = LocationUpdateRequest(latitude = 10.0, longitude = 20.0)
        val person = Person(id = personId, name = "John Doe", location = null)

        `when`(personsService.getById(personId)).thenReturn(person)
        val response: ResponseEntity<PersonDto> = controller.updateLocation(personId, request)

        verify(personsService).getById(personId)
        verify(locationsService).addLocation(person)

        assert(response.statusCode == HttpStatus.ACCEPTED)
        assert(response.body?.location?.latitude == 10.0)
        assert(response.body?.location?.longitude == 20.0)
    }

    @Test
    fun `deleteLocation should remove person's location and return OK`() {
        val personId = 2L
        val response: ResponseEntity<Long> = controller.deleteLocation(personId)

        verify(locationsService).removeLocation(personId)
        assert(response.statusCode == HttpStatus.OK)
        assert(response.body == personId)
    }
}