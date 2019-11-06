package com.touchlane.addressbook.domain

import com.touchlane.addressbook.db.model.DbContact
import com.touchlane.addressbook.domain.model.DomainContact

/**
 * This utils class can be used in order to transform DTO objects e.g. DomainModel
 * is general contact object, but for DB layer we need another DbContact object
 * and also for REST layer we might need another  ApiContact. So, DomainContact
 * can represent and adopt any of them
 */

fun toDomain(dbContact: DbContact): DomainContact =
    DomainContact(
        id = dbContact.id,
        firstName = dbContact.firstName,
        lastName = dbContact.lastName,
        email = dbContact.email,
        cellPhone = dbContact.phone,
        address = dbContact.address
    )

fun fromDomain(domainContact: DomainContact): DbContact =
    DbContact(
        id = domainContact.id,
        firstName = domainContact.firstName,
        lastName = domainContact.lastName,
        email = domainContact.email,
        phone = domainContact.cellPhone,
        address = domainContact.address
    )
