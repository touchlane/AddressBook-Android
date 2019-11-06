package com.touchlane.addressbook.domain.model

data class DomainContact(
    val id: Long? = null,
    val firstName: String,
    val lastName: String,
    val email: String,
    val address: String,
    val cellPhone: String
)