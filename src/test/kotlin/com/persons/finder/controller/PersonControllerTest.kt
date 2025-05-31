package com.persons.finder.controller

import com.persons.finder.domain.Person
import com.persons.finder.dto.request.CreatePersonRequest
import com.persons.finder.dto.response.PersonsNearbyResponse
import com.persons.finder.services.LocationsService
import com.persons.finder.services.PersonsService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class PersonControllerTest {

    private val personsService: PersonsService = mock(PersonsService::class.java)
    private val locationsService: LocationsService = mock(LocationsService::class.java)
    private val personController = PersonController(personsService, locationsService)


    @Test
    fun `should return nearby persons`() {
        val mockResult = listOf(PersonsNearbyResponse(name = "Jane"))
        `when`(locationsService.findNearbyPersons(1.0, 2.0, 10.0)).thenReturn(mockResult)

        val response: ResponseEntity<List<PersonsNearbyResponse>?> = personController.getNearby(1.0, 2.0, 10.0)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertNotNull(response.body)
        assertEquals(1, response.body!!.size)
        assertEquals("Jane", response.body!![0].name)
    }

    @Test
    fun `should return list of persons by ids`() {
        val person1 = Person(id = 1L, name = "Alice")
        val person2 = Person(id = 2L, name = "Bob")

        `when`(personsService.getByIds(listOf(1L, 2L))).thenReturn(listOf(person1, person2))

        val response = personController.getPersonsByIds(listOf(1L, 2L))

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(2, response.body?.size)
    }
    inline fun <reified T> anyNonNull(): T = Mockito.any(T::class.java) ?: throw NullPointerException("Mockito.any() returned null")

}

