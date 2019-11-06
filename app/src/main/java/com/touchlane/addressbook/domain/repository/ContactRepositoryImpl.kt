package com.touchlane.addressbook.domain.repository

import com.touchlane.addressbook.db.service.ContactService
import com.touchlane.addressbook.domain.fromDomain
import com.touchlane.addressbook.domain.model.DomainContact
import com.touchlane.addressbook.domain.toDomain
import io.reactivex.Observable
import io.reactivex.Single

class ContactRepositoryImpl(private val dbService: ContactService) :
    ContactRepository {

    override fun save(contact: DomainContact): Single<DomainContact> {
        return dbService.save(fromDomain(contact)).map(::toDomain)
    }

    override fun loadAll(): Observable<List<DomainContact>> {
        return dbService.loadAll().map { it.map(::toDomain) }
    }

    override fun search(name: String): Observable<List<DomainContact>> {
        return dbService.loadAll().map { it.map(::toDomain) }
    }

    override fun findById(id: Long): Single<DomainContact> {
        return dbService.findById(id).map(::toDomain)
    }
}