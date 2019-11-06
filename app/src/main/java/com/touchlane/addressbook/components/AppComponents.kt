package com.touchlane.addressbook.components

import android.content.Context
import androidx.room.Room
import com.touchlane.addressbook.AddressBookApplication
import com.touchlane.addressbook.db.AddressBookDatabase
import com.touchlane.addressbook.db.dao.ContactDAO
import com.touchlane.addressbook.db.service.ContactService
import com.touchlane.addressbook.db.service.ContactServiceImpl
import com.touchlane.addressbook.domain.repository.ContactRepository
import com.touchlane.addressbook.domain.repository.ContactRepositoryImpl

private val addressBookDatabase = buildAddressBookDb()

private fun buildAddressBookDb(): AddressBookDatabase {
    return Room.databaseBuilder(
        appContext(),
        AddressBookDatabase::class.java, "address-book.db"
    ).build()
}

fun addressBookDB() = addressBookDatabase

fun appContext(): Context = AddressBookApplication.instance

fun contactDao(): ContactDAO = addressBookDB().contactDao()

fun contactService(): ContactService = ContactServiceImpl(contactDao())

fun contactRepository(): ContactRepository =
    ContactRepositoryImpl(contactService())