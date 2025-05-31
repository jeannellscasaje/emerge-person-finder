package com.persons.finder.controller

import com.persons.finder.domain.Location
import com.persons.finder.dto.request.LocationUpdateRequest
import com.persons.finder.services.LocationsService
import com.persons.finder.services.PersonsService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v1")
class LocationController (private val  personsService : PersonsService,
    private  val locationsService: LocationsService) {

         @PutMapping("/persons/{id}/location")
         fun updateLocation( @PathVariable id: Long,
                             @RequestBody request: LocationUpdateRequest): ResponseEntity<Void> {
             val person = personsService.getById(id)
             val location = person.location ?: Location(latitude = request.latitude, longitude = request.longitude)
             location.latitude = request.latitude
             location.longitude = request.longitude
             person.location = location

             locationsService.addLocation(person)
             return ResponseEntity.noContent().build()
         }

         @DeleteMapping("/remove/{id}/location")
         fun deleteLocation(@PathVariable id: Long): ResponseEntity<Void> {
             locationsService.removeLocation(id)
             return ResponseEntity.noContent().build()
    }
}