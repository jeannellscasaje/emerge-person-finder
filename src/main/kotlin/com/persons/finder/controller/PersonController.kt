package com.persons.finder.controller

import com.persons.finder.domain.Person
import com.persons.finder.dto.request.CreatePersonRequest
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

@RestController
@RequestMapping("api/v1/persons")
class PersonController (private val personsService: PersonsService,
                             private val locationService: LocationsService) {


    @PostMapping("")
    fun createPerson(@RequestBody request: CreatePersonRequest): ResponseEntity<String> {
        val newPerson = Person(name = request.name)
        personsService.save(newPerson)
        return ResponseEntity.status(HttpStatus.CREATED).body(newPerson.name)
    }


        @GetMapping("/locations/nearby")
        fun getNearby(
        @RequestParam lat: Double,
        @RequestParam lon: Double,
        @RequestParam radiusKm: Double): ResponseEntity<List<PersonsNearbyResponse>?> {
            val nearbyNames = locationService.findNearbyPersons(lat, lon, radiusKm)
            return ResponseEntity.ok(nearbyNames)
        }

        @GetMapping
        fun getPersonsByIds(@RequestParam ids: List<Long>): ResponseEntity<List<Person>> {
            val persons = personsService.getByIds(ids)
            return ResponseEntity.ok(persons)
        }


}