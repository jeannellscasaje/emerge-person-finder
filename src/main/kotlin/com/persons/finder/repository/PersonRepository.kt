package com.persons.finder.repository

import com.persons.finder.domain.Person
import org.springframework.data.jpa.repository.JpaRepository

interface PersonRepository : JpaRepository<Person, Long>