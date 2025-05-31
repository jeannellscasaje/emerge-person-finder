package com.persons.finder.services

import com.persons.finder.domain.Person
import com.persons.finder.repository.PersonRepository
import org.springframework.stereotype.Service

@Service
class PersonsServiceImpl (private val personRepository: PersonRepository) : PersonsService {

    override fun getById(id: Long): Person {
        return personRepository.findById(id).orElseThrow {
            NoSuchElementException("Person with ID $id not found")
        }
    }

    override fun getByIds(ids: List<Long>): List<Person> {
        val persons = personRepository.findAllById(ids).toList()
        if (persons.size != ids.size) {
            val foundIds = persons.map { it.id }
            val missingIds = ids.filterNot { foundIds.contains(it) }
            throw NoSuchElementException("Persons with IDs $missingIds not found")
        }
        return persons
    }

    override fun save(person: Person) {
        personRepository.save(person)
    }

}