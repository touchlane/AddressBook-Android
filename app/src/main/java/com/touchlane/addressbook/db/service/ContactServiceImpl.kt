package com.touchlane.addressbook.db.service

import android.database.sqlite.SQLiteConstraintException
import com.touchlane.addressbook.db.dao.ContactDAO
import com.touchlane.addressbook.db.model.DbContact
import com.touchlane.addressbook.exception.ContactNotValidException
import com.touchlane.addressbook.exception.ContactWithSuchEmailAlreadyExistsException
import com.touchlane.addressbook.isValid

class ContactServiceImpl(private val contactDAO: ContactDAO) : ContactService {

    override fun save(contact: DbContact): DbContact {
        ensureValid(contact)
            .let (::saveInternal)
        return contact
    }

    override fun loadAll(): List<DbContact> {
        return contactDAO.loadAll()
    }

    override fun search(name: String): List<DbContact> {
        return contactDAO.search("%$name%")
    }

    override fun findById(id: Long): DbContact? {
        return contactDAO.findById(id)
    }

    private fun saveInternal(dbContact: DbContact): DbContact {
        try {
            val id = contactDAO.save(dbContact)
            dbContact.id = id
            return dbContact
        } catch (e: SQLiteConstraintException) {
            throw ContactWithSuchEmailAlreadyExistsException()
        }
    }

    private fun ensureValid(contact: DbContact): DbContact {
        if (isValid(contact).not()) {
            throw ContactNotValidException()
        }
        return contact
    }
}