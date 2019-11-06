package com.touchlane.addressbook.domain.repository

import com.touchlane.addressbook.db.model.DbContact
import com.touchlane.addressbook.db.service.ContactService
import com.touchlane.addressbook.domain.fromDomain
import com.touchlane.addressbook.domain.model.DomainContact
import com.touchlane.addressbook.domain.toDomain
import com.touchlane.addressbook.exception.ContactNotFoundException
import io.reactivex.Observable
import io.reactivex.Single

class ContactRepositoryImpl(private val dbService: ContactService) :
    ContactRepository {

    override fun save(contact: DomainContact): Single<DomainContact> {
        return Single.create {
            try {
                val saved = dbService.save(fromDomain(contact))
                it.onSuccess(contact.copy(id = saved.id))
            } catch (e: Throwable) {
                it.onError(e)
            }
        }
    }

    override fun loadAll(): Observable<List<DomainContact>> {
        return adapt { dbService.loadAll() }
    }

    override fun search(name: String): Observable<List<DomainContact>> {
        return adapt { dbService.search(name) }
    }

    override fun findById(id: Long): Single<DomainContact> {
        return Single.create {
            try {
                val contact = dbService.findById(id)
                if (contact == null) {
                    it.onError(ContactNotFoundException())
                } else {
                    it.onSuccess(contact.let(::toDomain))
                }
            } catch (e: Throwable) {
                it.onError(e)
            }
        }
    }

    private fun adapt(loader: () -> List<DbContact>): Observable<List<DomainContact>> {
        val dbContacts: Observable<List<DbContact>> = Observable.create {
            it.onNext(loader.invoke())
        }
        return dbContacts.map { it.map(::toDomain) }
    }
}