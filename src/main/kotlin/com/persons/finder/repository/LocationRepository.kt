package com.persons.finder.repository

import com.persons.finder.domain.Location
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface LocationRepository : JpaRepository<Location, Long> {

    @Query(
        value = """
            SELECT p.name
            FROM person p
            JOIN location l ON p.id = l.person_id
            WHERE (
                6371 * acos(
                    cos(radians(:latitude)) * cos(radians(l.latitude)) *
                    cos(radians(l.longitude) - radians(:longitude)) +
                    sin(radians(:latitude)) * sin(radians(l.latitude))
                )
            ) <= :radiusKm
            ORDER BY
            6371 * acos(
                cos(radians(:latitude)) * cos(radians(l.latitude)) *
                cos(radians(l.longitude) - radians(:longitude)) +
                sin(radians(:latitude)) * sin(radians(l.latitude))
            )
        """,
        nativeQuery = true
    )
    fun findPersonNamesWithinRadiusSorted(
        @Param("latitude") latitude: Double,
        @Param("longitude") longitude: Double,
        @Param("radiusKm") radiusKm: Double
    ): List<String>
}
