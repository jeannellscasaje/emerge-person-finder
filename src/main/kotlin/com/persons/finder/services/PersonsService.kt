package com.persons.finder.services

import com.persons.finder.domain.Person

interface PersonsService {
    fun getById(id: Long): Person
    fun save(person: Person)
}