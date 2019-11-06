package com.touchlane.addressbook.db.service

import android.database.sqlite.SQLiteConstraintException
import com.touchlane.addressbook.db.dao.ContactDAO
import com.touchlane.addressbook.db.model.DbContact
import com.touchlane.addressbook.exception.ContactNotValidException
import com.touchlane.addressbook.exception.ContactWithSuchEmailAlreadyExistsException
import com.touchlane.addressbook.isValid
import io.reactivex.Observable
import io.reactivex.Single

class ContactServiceImpl(private val contactDAO: ContactDAO) : ContactService {

    override fun save(contact: DbContact): Single<DbContact> {
        return ensureValid(contact)
            .let (::saveInternal)
    }

    override fun search(name: String): Observable<List<DbContact>> {
        return if (name.isBlank()) {
            contactDAO.loadAll()
        } else {
            contactDAO.search("%$name%")
        }
    }

    override fun findById(id: Long): Single<DbContact> {
        return contactDAO.findById(id)
    }

    private fun saveInternal(dbContact: DbContact): Single<DbContact> {
        return contactDAO.save(dbContact)
            .map {
                dbContact.id = it
                dbContact
            }
            .onErrorResumeNext {
                return@onErrorResumeNext if (it is SQLiteConstraintException) {
                    Single.error<DbContact>(ContactWithSuchEmailAlreadyExistsException())
                } else {
                    Single.error(it)
                }
            }
    }

    private fun ensureValid(contact: DbContact): DbContact {
        if (isValid(contact).not()) {
            throw ContactNotValidException()
        }
        return contact
    }
}