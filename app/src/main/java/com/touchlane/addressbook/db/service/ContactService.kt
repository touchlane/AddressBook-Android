package com.touchlane.addressbook.db.service

import com.touchlane.addressbook.db.model.DbContact

interface ContactService {

    fun save(contact: DbContact): DbContact

    fun loadAll(): List<DbContact>

    fun search(name: String): List<DbContact>

    fun findById(id: Long): DbContact?
}