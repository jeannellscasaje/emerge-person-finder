package com.persons.finder.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.persons.finder.domain.Location
import com.persons.finder.domain.Person
import com.persons.finder.dto.request.LocationUpdateRequest
import com.persons.finder.repository.PersonRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@SpringBootTest
@AutoConfigureMockMvc
class LocationControllerIntegrationTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var personRepository: PersonRepository

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Test
    fun `PUT - update location of a person`() {

        val person = Person(name = "Sam")
        val savedPerson = personRepository.save(person)

        val locationRequest = LocationUpdateRequest(latitude = 40.3456, longitude = 56.9012)
        val jsonRequest = objectMapper.writeValueAsString(locationRequest)

        mockMvc.perform(
            put("/api/v1/persons/${savedPerson.id}/location")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest)
        )
            .andExpect(status().isAccepted)
            .andExpect(jsonPath("$.name").value("Sam"))
            .andExpect(jsonPath("$.location.latitude").value(40.3456))
            .andExpect(jsonPath("$.location.longitude").value(56.9012))
    }

    @Test
    fun `DELETE - remove location of a person`() {

        val person = Person(name = "Sam", location = Location(latitude = 10.0, longitude = 20.0))
        val savedPerson = personRepository.save(person)

        mockMvc.perform(delete("/api/v1/remove/${savedPerson.id}/location"))
            .andExpect(status().isOk)
            .andExpect(content().string(savedPerson.id.toString()))
    }
}
