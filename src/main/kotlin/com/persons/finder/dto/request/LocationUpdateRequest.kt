package com.persons.finder.dto.request

import javax.validation.constraints.NotNull

data class LocationUpdateRequest(
    @field:NotNull(message = "Latitude must not be null")
    val latitude: Double,
    @field:NotNull(message = "Longitude must not be null")
    val longitude: Double
)