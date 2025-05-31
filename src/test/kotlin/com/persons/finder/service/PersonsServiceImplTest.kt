package com.persons.finder.service

import com.persons.finder.domain.Person
import com.persons.finder.repository.PersonRepository
import com.persons.finder.services.PersonsServiceImpl
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.*
import java.util.*

class PersonsServiceImplTest {

    private val personRepository = mock(PersonRepository::class.java)
    private val service = PersonsServiceImpl(personRepository)

    @Test
    fun `getById should return person when found`() {
        val person = Person(id = 1L, name = "John")
        `when`(personRepository.findById(1L)).thenReturn(Optional.of(person))

        val result = service.getById(1L)

        assertEquals(person, result)
    }

    @Test
    fun `getById should throw when not found`() {
        `when`(personRepository.findById(1L)).thenReturn(Optional.empty())

        val exception = assertThrows<NoSuchElementException> {
            service.getById(1L)
        }

        assertEquals("Person with ID 1 not found", exception.message)
    }

    @Test
    fun `getByIds should return list of persons when all found`() {
        val persons = listOf(
            Person(id = 1L, name = "John"),
            Person(id = 2L, name = "Jane")
        )
        val ids = listOf(1L, 2L)

        `when`(personRepository.findAllById(ids)).thenReturn(persons)

        val result = service.getByIds(ids)

        assertEquals(persons, result)
    }

    @Test
    fun `getByIds should throw when some persons are missing`() {
        val foundPersons = listOf(Person(id = 1L, name = "John"))
        val ids = listOf(1L, 2L)

        `when`(personRepository.findAllById(ids)).thenReturn(foundPersons)

        val exception = assertThrows<NoSuchElementException> {
            service.getByIds(ids)
        }

        assertEquals("Persons with IDs [2] not found", exception.message)
    }

    @Test
    fun `save should call repository save`() {
        val person = Person(id = 3L, name = "Alice")

        service.save(person)

        verify(personRepository).save(person)
    }
}