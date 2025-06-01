package com.persons.finder.controller

import com.persons.finder.domain.Person
import com.persons.finder.dto.LocationDto
import com.persons.finder.dto.PersonDto
import com.persons.finder.dto.request.CreatePersonRequest
import com.persons.finder.dto.response.CreatePersonResponse
import com.persons.finder.dto.response.PersonsNearbyResponse
import com.persons.finder.services.LocationsService
import com.persons.finder.services.PersonsService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.data.domain.PageRequest
import javax.validation.Valid
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

@RestController
@RequestMapping("api/v1/persons")
class PersonController(
    private val personsService: PersonsService,
    private val locationService: LocationsService
) {

    @Operation(summary = "Create a new person")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "201", description = "Person created successfully"),
            ApiResponse(responseCode = "400", description = "Invalid input")
        ]
    )
    @PostMapping("")
    fun createPerson(@Valid @RequestBody request: CreatePersonRequest): ResponseEntity<CreatePersonResponse> {
        val newPerson = Person(name = request.name)
        personsService.save(newPerson)
        return ResponseEntity.status(HttpStatus.CREATED).body(CreatePersonResponse(newPerson.name))
    }

    @Operation(summary = "Get nearby persons within radius")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Successfully retrieved nearby persons"),
            ApiResponse(responseCode = "400", description = "Invalid query parameters")
        ]
    )
    @GetMapping("/locations/nearby")
    fun getNearby(
        @RequestParam @NotNull lat: Double,
        @RequestParam @NotNull lon: Double,
        @RequestParam @NotNull @Min(0) radiusKm: Double,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): ResponseEntity<List<PersonsNearbyResponse>> {
        val pageable = PageRequest.of(page, size)
        val nearbyPage = locationService.findNearbyPersons(lat, lon, radiusKm, pageable)
        return ResponseEntity.ok(nearbyPage.content)
    }


    @Operation(summary = "Get persons by list of IDs")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Successfully retrieved persons"),
            ApiResponse(responseCode = "400", description = "Invalid person IDs")
        ]
    )
    @GetMapping
    fun getPersonsByIds(@RequestParam ids: List<Long>): ResponseEntity<List<PersonDto>> {
        val persons = personsService.getByIds(ids)
        val response = persons.map { person ->
            PersonDto(
                name = person.name,
                location = person.location?.let {
                    LocationDto(
                        latitude = it.latitude,
                        longitude = it.longitude
                    )
                }
            )
        }
        return ResponseEntity.ok(response)
    }
}
