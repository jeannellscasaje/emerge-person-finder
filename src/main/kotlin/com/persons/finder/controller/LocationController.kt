package com.persons.finder.controller

import com.persons.finder.domain.Location
import com.persons.finder.dto.LocationDto
import com.persons.finder.dto.PersonDto
import com.persons.finder.dto.request.LocationUpdateRequest
import com.persons.finder.services.LocationsService
import com.persons.finder.services.PersonsService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses

@RestController
@RequestMapping("api/v1")
class LocationController(
    private val personsService: PersonsService,
    private val locationsService: LocationsService
) {

    @Operation(summary = "Update the location of a person by ID")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "202", description = "Location updated successfully"),
            ApiResponse(responseCode = "400", description = "Invalid input data"),
            ApiResponse(responseCode = "404", description = "Person not found")
        ]
    )
    @PutMapping("/persons/{id}/location")
    fun updateLocation(
        @PathVariable id: Long,
        @Valid @RequestBody request: LocationUpdateRequest
    ): ResponseEntity<PersonDto> {
        val person = personsService.getById(id)
        val location = person.location ?: Location(latitude = request.latitude, longitude = request.longitude)
        location.latitude = request.latitude
        location.longitude = request.longitude
        person.location = location

        locationsService.addLocation(person)

        val response = PersonDto(
            name = person.name,
            location = person.location?.let {
                LocationDto(
                    latitude = it.latitude,
                    longitude = it.longitude
                )
            }
        )

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response)
    }

    @Operation(summary = "Delete location of a person by ID")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Location deleted successfully"),
            ApiResponse(responseCode = "404", description = "Person or location not found")
        ]
    )
    @DeleteMapping("/remove/{id}/location")
    fun deleteLocation(@PathVariable id: Long): ResponseEntity<Long> {
        locationsService.removeLocation(id)
        return ResponseEntity.status(HttpStatus.OK).body(id)
    }
}
