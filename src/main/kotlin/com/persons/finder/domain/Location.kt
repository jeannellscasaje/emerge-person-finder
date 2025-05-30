package com.persons.finder.domain

data class Location(
    // Tip: Person's id can be used for this field
    val referenceId: Long,
    val latitude: Double,
    val longitude: Double
)