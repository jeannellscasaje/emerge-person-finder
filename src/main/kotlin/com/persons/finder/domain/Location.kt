package com.persons.finder.domain

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Index
import javax.persistence.JoinColumn
import javax.persistence.OneToOne
import javax.persistence.Table

@Entity
@Table(
    name = "location",
    indexes = [
        Index(name = "idx_location_lat_lon", columnList = "latitude, longitude"),
        Index(name = "idx_location_person_id", columnList = "person_id")
    ]
)
data class Location(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    var latitude: Double,
    var longitude: Double,

    @OneToOne
    @JoinColumn(name = "person_id")
    var person: Person? = null
)