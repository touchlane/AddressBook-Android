package com.touchlane.addressbook.domain.repository

import com.touchlane.addressbook.domain.model.DomainContact
import io.reactivex.Observable
import io.reactivex.Single

interface ContactRepository {

    fun save(contact: DomainContact): Single<DomainContact>

    fun search(name: String): Observable<List<DomainContact>>

    fun findById(id: Long): Single<DomainContact>
}