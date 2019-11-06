package com.touchlane.addressbook.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.touchlane.addressbook.db.dao.ContactDAO
import com.touchlane.addressbook.db.model.DbContact


@Database(entities = [DbContact::class], version = 1)
abstract class AddressBookDatabase : RoomDatabase() {
    abstract fun contactDao(): ContactDAO
}
