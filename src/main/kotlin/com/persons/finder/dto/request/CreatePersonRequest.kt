package com.persons.finder.dto.request

import javax.validation.constraints.NotBlank

data class CreatePersonRequest(
    @field:NotBlank(message = "Name must not be blank")
    val name: String
)