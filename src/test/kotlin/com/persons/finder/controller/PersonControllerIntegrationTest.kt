package com.persons.finder.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.persons.finder.dto.request.CreatePersonRequest
import com.persons.finder.services.LocationsService
import com.persons.finder.services.PersonsService
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
class PersonControllerIntegrationTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Autowired
    private lateinit var personsService: PersonsService

    @Autowired
    private lateinit var locationsService: LocationsService

    @Test
    fun `should create a person successfully`() {
        val request = CreatePersonRequest(name = "Sam")
        val requestJson = objectMapper.writeValueAsString(request)

        mockMvc.perform(post("/api/v1/persons")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestJson))
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.name").value("Sam"))
    }

}
