package com.persons.finder

import com.persons.finder.domain.Location
import com.persons.finder.domain.Person
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@Component
class DataSeeder(
    private val entityManager: EntityManager
) : CommandLineRunner {

    @Transactional
    override fun run(vararg args: String?) {
        println("Seeder running")

        val total = 1000

        for (i in 1..total) {
            val person = Person(name = "Person $i")
            entityManager.persist(person)

            val latitude =  -90 + (i % 180) + Math.random()
            val longitude = -180 + (i % 360) + Math.random()

            val location = Location(latitude = latitude, longitude = longitude, person = person)
            entityManager.persist(location)

            if (i % 100 == 0) {

                entityManager.flush()
                entityManager.clear()
                println("Seeded $i persons and locations")
            }
        }

        println("✅ Seeded $total persons each with own location")
    }
}