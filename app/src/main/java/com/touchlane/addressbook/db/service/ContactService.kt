package com.touchlane.addressbook.db.service

import com.touchlane.addressbook.db.model.DbContact
import io.reactivex.Observable
import io.reactivex.Single

interface ContactService {

    fun save(contact: DbContact): Single<DbContact>

    fun search(name: String): Observable<List<DbContact>>

    fun findById(id: Long): Single<DbContact>
}